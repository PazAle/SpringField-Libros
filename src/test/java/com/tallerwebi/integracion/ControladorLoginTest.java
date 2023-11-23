/*package com.tallerwebi.integracion;

import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ServicioImagen;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.dominio.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorLoginTest {

	private Usuario usuarioMock;
	@Autowired
	private WebApplicationContext wac;
	private ServicioImagen servicioImagenMock;
	private MockMvc mockMvc;


	@BeforeEach
	public void init(){
		usuarioMock = mock(Usuario.class);
		servicioImagenMock = mock(ServicioImagen.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void debeRetornarLaPaginaLoginCuandoSeNavegaALaRaiz() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/"))
				//.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
		assertThat("redirect:/home", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
		assertThat(true, is(modelAndView.getModel().isEmpty()));
	}

	@Test
	public void debeRetornarLaPaginaLoginCuandoSeNavegaALLogin() throws Exception {
		// Configura el comportamiento del mock de servicioImagen
		Imagen imagenLogoMock = mock(Imagen.class);
		//when(imagenLogoMock.getImagenBase64()).thenReturn("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAF3CAYAAABT8rn8AAAAAXNSR0IArs4c6QAAIABJREFUeF7snQd8FcX2x8/M7t6b5KaAoPCQEEBAeq8+qu/vw66I6LNXpIZQVIoiPbRAQKo0FQQxNAlIsYESEkqUXhJCCwIipJHklm3z/8zeGwwYIQqR3N0Tnw9D9s7O+Z7Z/PbMnDlDAL+QABJAAkgACSABvydA/N4CNAAJIAEkgASQABIAFHQcBEgACSABJIAETEAABd0ETkQTkAASQAJIAAmgoOMYQAJIAAkgASRgAgIo6CZwIpqABJAAEkACSAAFHccAEkACSAAJIAETEEBBN4ET0QQkgASQABJAAijoOAaQABJAAkgACZiAAAq6CZyIJiABJIAEkAASQEHHMYAEkAASQAJIwAQEUNBN4EQ0AQkgASSABJAACjqOASSABJAAEkACJiCAgm4CJ6IJSAAJIAEkgARQ0HEMIAEkgASQABIwAQEUdBM4EU1AAkgACSABJICCjmMACSABJIAEkIAJCKCgm8CJaAISQAJIAAkgARR0HANIAAkgASSABExAAAXdBE5EE5AAEkACSAAJoKDjGEACSAAJIAEkYAICKOgmcCKagASQABJAAkgABR3HABJAAkgACSABExBAQTeBE9EEJIAEkAASQAIo6DgGkAASQAJIAAmYgAAKugmciCYgASSABJAAEkBBxzGABJAAEkACSMAEBFDQTeBENAEJIAEkgASQAAo6jgEkgASQABJAAiYggIJuAieiCUgACSABJIAEUNBxDCABJIAEkAASMAEBFHQTOBFNQAJIAAkgASSAgo5jAAkgASSABJCACQigoJvAiWgCEkACSAAJIAEUdBwDSAAJIAEkgARMQAAF3QRORBOQABJAAkgACaCg4xhAAkgACSABJGACAijoJnAimoAEkAASQAJIAAUdxwASQAJIAAkgARMQQEE3gRPRBCSABJAAEkACKOg4BpAAEkACSAAJmIAACroJnIgmIAEkgASQABJAQccxgASQABJAAkjABARQ0E3gRDQBCSABJIAEkAAKOo4BJIAEkAASQAImIICCbgInoglIAAkgASSABFDQcQwgASSABJAAEjABARR0EzgRTUACSAAJIAEkgIKOYwAJIAEkgASQgAkIoKCbwIloAhJAAkgACSABFHQcA0gACSABJIAETEAABd0ETkQTkAASQAJIAAmgoOMYQAJIAAkgASRgAgIo6CZwIpqABJAAEkACSAAFHccAEkACSAAJIAETEEBBN4ET0QQkgASQABJAAijoOAaQABJAAkgACZiAAAq6CZyIJiABJIAEkAASQEHHMYAEkAASQAJIwAQEUNBN4EQ0AQkgASSABJAACjqOASSABJAAEkACJiCAgm4CJ6IJSAAJIAEkgARQ0HEMIAEkgASQABIwAQEUdBM4EU1AAkgACSABJICCjmMACSABJIAEkIAJCKCgm8CJaAISQAJIAAkgARR0HANIAAkgASSABExAAAXdBE5EE5AAEkACSAAJoKDjGEACSAAJIAEkYAICKOgmcCKagASQABJAAkgABR3HABJAAkgACSABExBAQTeBE9EEJIAEkAASQAIo6DgGkAASQAJIAAmYgAAKugmciCYgASSABJAAEkBBxzGABJAAEkACSMAEBFDQTeBENAEJIAEkgASQAAo6jgEkgASQABJAAiYggIJuAieiCUgACSABJIAEUNBxDCABJIAEkAASMAEBFHQTOBFNQAJIAAkgASSAgo5jAAkgASSABJCACQigoJvAiWgCEkACSAAJIAEUdBwDSAAJIAEkgARMQAAF3QRORBOQABJAAkgACaCg4xhAAkgACSABJGACAijoJnAimoAEkAASQAJIAAUdxwASQAJIAAkgARMQQEE3gRPRBCSABJAAEkACKOg4BpAAEkACSAAJmIAACroJnIgmIAEkgASQABJAQccxgASQABJAAkjABARQ0E3gRDQBCSABJIAEkAAKOo4BJIAEkAASQAImIICCbgInoglIAAkgASSABFDQcQwgASSABJAAEjABARR0EzgRTUACSAAJIAEkgIKOYwAJIAEkgASQgAkIoKCbwIloAhJAAkgACSABFHQcA0gACSABJIAETEAABd0ETkQTkAASQAJIAAmgoOMYQAJIAAkgASRgAgIo6CZwIpqABJAAEkACSAAFHccAEkACSAAJIAETEEBBN4ET0QQkgASQABJAAijoOAaQABJAAkgACZiAAAq6CZyIJiABJIAEkAASQEHHMYAEkAASQAJIwAQEUNBN4EQ0AQkgASSABJAACjqOASSABJAAEkACJiCAgm4CJ6IJSAAJIAEkgARQ0HEMIAEkgASQABIwAQEUdBM4EU1AAkgACSABJICCjmMACSABJIAEkIAJCKCgm8CJaAISQAJIAAkgARR0HANIAAkgASSABExAAAXdBE5EE5AAEkACSAAJoKDjGEACSAAJIAEkYAICKOgmcCKagASQABJAAkgABR3HABJAAkgACSABExBAQTeBE9EEJIAEkAASQAIo6DgGkAASQAJIAAmYgAAKugmciCYgASSABJAAEkBBxzGABJAAEkACSMAEBFDQTeBENAEJIAEkgASQAAo6jgEkgASQABJAAiYggIJuAieiCUgACSABJIAEUNBxDCABJIAEkAASMAEBFHQTOBFNuJrAuXXJQW/0HbAi72Ju2zApGJhL0FRZ1ykFgQpUA50AEEIAdGDAgAJlDBjh/0WAMe8Pma4D0QkwQviP+LWEMv5jAiDohOiEGd8IAMbfMuN74K0XXMjvwSgAaLxNo2l+LSEUgGnUaNdo2fhMwcPIrnwGWKG2BPC1D7wHoDGd5RJRAE3VdFWm7FKFShF767VssKV+iwY/Pl7tfxfIM0T7s7Hx8asjA1LPXWx0Nu3MQ2dPn+wcKNgqUNBFVeW9C5UIEzRuMQFimEaACIwxnZtBAAwQDICC126dEG4zt5BRwogAhOkABhvg13g5EK0Ago8tZ8MZcIMEL0MQGHeP0RYHyRj/3nuJAZAyr/P497xNneP1NmJ0gbdp/JAA1TWmMl3TwWaz6YroJh5B1uxlA9Nj5k15o/6Drfbhs4MEzEQABd1M3kRbDALfzVl39+gBw7eVF0IjNI8MQbZQAI1LI4CsyiAQEbgsMeCa4/3Vf0V3imBoSEkh5fmz74uB37gTFyquPEU8fAVNF/xZZJPGWwAw0KgCRKBc0hhlAnF63DoEijlaIOy9p2ndL/7X/aXV9z1zX+Y1jZAXmnSrk336tzchX+kiqKSiTRRFyS4QVVOIzggTNYnwlw3ez0Kf9d726r8r+Avj2gJlvm7nr+3M32RrvHX5vFfQJGHcp973By75mqaDJEmgMw0ooeCU8wixU5ap58kjPox+rWOZB1dc76WnGP7ES5BAqSKAgl6q3IGduRUElg5ZVH3+hA83VZbK3cNF/Ay7eJYy8TdZk+0OR6CkyBoQxrhm+SJIIzLnusgDQ0PsAZhuRIzeKJV4I1L+vSEkghGNeoNGHkXzyNRQkt+jbN4Yo4U+a4TrBa8ORujvVUx+icgI2CmASBgRC71meCNYHs8zb9jP+fBwnjAKUlAwqB5d1z0yuUMIsQkMJEEg4NSdkKs7s+q0qbds4ODI8dUeafkr/9zIkSPpmTV7mx8/eGxWebFMo0AhWFA1HZy6rOYyj4dJVJECBKK4nSAwg4a3e8akBA+AvUbymNgQcP5Tn+EGCd5bI4bnP/d92vca4GVl8NQN3fW26b2Q/6WXh/EhHXT+gqBRZpgKOmE89DZCcwOZMcPxu6BzX3hfy4y/500ySiizSzaa58pnoigS0JlUWSzvUD0qPa9lK+9M/KDHgy2e/Ix0IuqtGHPYBhIoDQRQ0EuDF7APt5TA5hnx1aYOGv/1nVpQdTdzO1t0bf/CK2/+77vz5zIll8NDBVEkgttFPKqNBkMw8P95cmQKDgDq8RCAINDtGgtUA5nL4yI0VCC6pjFd1Zio2aioK1S3acwJAAGqzgqut4uynpcHEBzsNYe3Tz0C4W2JmkJ1NYBBEADwDwYBUJkSKngIAdGmihBghwCbk7lsIhMZ0xmjAjUEilA+6w+iaKgYIRrTKCE24vaoRLKHqu7fLkqJ3yTUPJ56tMXZk2f+T85z3R3kcAguRXZWrlZl7ROvd3t/d96Bswdmb3vUfdk9LtBmr+l0OqkYJOTXqF8rqWLliG///UCnn8WQkAymywxABlFkTNM1Ktkk0PhqhU4p7w/vi6ppvBuEaCohgqTqVNdB8dos+frr1nVqoxIBUEFVCTGuoUyhsqLpVNAJ5Q0pxMPtBKIF2qimK4LusfN5ekUj+UxVBe+7DRUoobJMPCIloqZSVRepIIkGG1FQdU1WjesEm0iJQgnYARRKiS7rgl2lpEzZUHVj/IYmmxdvnBpEHJWyaJ4yIHpw7wcbPfYpCvotffSwsdtMAAX9NjsAb3/rCawcv7T6rJEffldJL1vFpTnz63Vt2XnMipikW3+n0tUiY0xYOe7jVktmfjZeznS1thG7oOq68tBDDy7Jzvxt2Y5dydMEGlDfo3pI0F3B2S/0+t+Err1f/oiUI5dLlyW3vjfvd+lfZ9+GnzbaSWCVSyRHjRr/bq8no576hL+W3Pq7YYtI4PYQQEG/PdzxriVIYM2k5fdM/yD2uwp6+XC36s5v2KXJA2NWxewswVuWmqbZSEbX2z6vNT924WQqkwdc+fmiXbS5gamXZU0vL9mDhJA7y/76bI//jXn8ta5LSEWSX2o6X4IdGdllYK3D8bs2BRJbxAWSo/SbMOSthwY+/RkxFhDwCwmYgwAKujn8iFYUIrBy6tLqH743fcudrEK4U3XnN+7S/L/jV4w3fYReeBDM7B5da/2y9Z87hIBGRNWorqtAbTa4rLidPYb2H/3U8GemWCk6HdMt6t6jq5O+DmS28PM0X4mc8H6fzoOe+thKDPCXhPkJoKCb38eWs3D9+KXVp4ya/k05uKuqU3XnNenS7JFxKyYmWAnEli1bxJUDF7x84kjq1EDNFiJQBrng0Zu1a5vwSv833qj1aMMTVuIxsltk3UNrd24IYPYqF4hL6Tfh/QGPNHliHq6hW2kUmN9WFHTz+9hyFm6e8mX41CHjvw2jZWvk666cf3e577FhceO3Ww3Ezk+/LTd98MSlnt9c/8eYSoL+FXq539hhg1u81mERIdbK7o5+blDtpDXbN9hpYESm7tbeHTvi/c6DOscSQnzpfFYbHWivGQmgoJvRqxayKWFhQsjPew/ddSb1l0qXzp6vBE53UHlH0J3HU4/2E1SoSAhxVqp+93yPoh3RAwTZxVzEZgtkuuymfM8a0RnjWVGyUe3FTiQSqLmISwc7dT/T/ZnEx/s+fp7wXWt++MUYo69WeXx0/rnLb1OiSiHh5U4v2LX2QXInSf0nzFkwYMEdP2z8pmOIzRGsK27m1twAug6qrvOkd1XVmfEPoYRSoIT/afwDulEigFFqlO1hCi/yozPuB4EIxlZDDXRKdNBkHTQqMGLjvtR0xtvUBcJEkTCPRyV3OMqrWVmXhDLBZape/DXjHVllFRRR1+o3arjBmXf5K1l1Xq58T3hWeK3wC/+qWvHcQ5HdMnBd/Z8YHXiPkiCAgl4SVLHNEiew47MNod+v/fq/P2z9+X9ut+1eQZPKh0kBDup2CYLmoaApNpsgGYVkCLGpwERGbCKoRGNOJR8CwQYC3yZubGAmTCWECkQAygiogsbyBLeiBMqbB78/8q0HBz54bXGWErfvVt3gjepdBmSfyR5HQbOXq1Hx8JwtX/yHVCS/3ar2/6ydj0d+HLB07uKPbC7hSbsqSeBRGdWZdx+et5iMtwaM73uemebd7O4tUFP4Z9dca/zcqCIHoGt81zuv8aczEH0Vd/iedg+XfAZgpxJTNZUEk0BymTntApUoNeoGaJpIecUZRXczl6JTmkfDAo7/+6H/W/Xw813WNOjc4ExJM8L2kcCtJoCCfquJYnslSiD5o2RpxapFLS4eP97z7NmLnUF0lBUgkBBZJwE6I5ImA2GqUQ2O75a2UQejugiESpAL+UYVE0kQgCmyVxWMQiYEVEKN2qMCU0GXgDltHpYjOHe9N2HYk517PVXiAlhS0N6s878+F1N/m0hBC6zcKCLxw+8/fZyUIVkldb+CdtfErikTOy7mW5tLaGRXRSrJFCS+id0oz+qTdOPPguqvBTJe+PtCZXqulXjfZ40UdaPSD68Zy78zqtMAiBJQQQC3ywOhAWEgyy7+xgZM00EjCvCN83zHmggaE/mcACHgYYTlqbK7bHjF5Kb/abmk44MPxjd9punFkmaF7SOBW0UABf1WkcR2SpwAL8rWp+ULz6fsPzrKromVg2yhgkfz6BpoOXai/Uo8+SQQqD2ABJRVib2sDIHEres62LSzECBm5bpzgzWqU03XVTslOuXqblQi44VPeHkTJtoppQqRIahiaErdNg1mPPDGE5s7derkt9XEIpu+0SN9f/oUQvSAe1rU3Bizes7//omtajwpb+fKhO5b1343TM1xlxFBZKrCX52MGnE8eDaq9flCdW/Qzd+tfLX0fXXbi1jqIPwzvHaeUalOp96AXdR1XdANb1KNAlN5wXlBJAKIjBfFYapqC5EDbQ4xkP6mXWCS3ebSmEcHTQE7EBoEAQGqymigEAouXdV/k3LymrRtvWbypzOHk3BytsQHN94ACdwCAijotwAiNlHyBA7GHbR9PDv2uYM79o5wkOBwqjg0mbrTAgKF9c1aNPiy15Dux5xHDlTKP/dr2x07j3Rf+31yQ52WI3IAcdZpd0+PCVNmrIR6wODQVfXJf+94PQBIAwK/nCJQtSpABmjQDFR/XT8vMGxgi97d0/Ycj+WCXrttvbUTlsa+RCoRXquuxL8YYxKcc1UEWXCAzcZA9Kig6QrYAvlpKt4au04n4+fMQCDTQQ/WAXJ9B+f4umdU3CUE8oEYJfj4Z1xO78w9/1mgQweW5zsrxltKj/8oLz+fEKPSXhA4QhzCF9MW1Yub/NlCm26r5A6QnZEjBg27/9H/rIfgAOWr2fPbJn+zbeLlM1kVL1/IkRgTwWnTWTa43c3ub/Pti1GvvVv7oUYpJQ4Mb4AEbpIACvpNAsSP/zME+jd98/7TR1PmE02vIvMV0ADHzlr1aw179ukOu9u0r65eWLa+s+vcL4Oo3dH44C/5wZ9/t1eQxXLssuh21WoT8cKHWxav/Wd6WrruMqjlW2+eSj4WqxMScG+7eqvGb/nwNUKIq3T1suR78363d+89sG7HpkBNqpKj57r6Thzc6+FmT30OHUHbOXryU+yXjFmSE8qnp6bTpP37IE8uyzJtQZBBVLnz0w99/Grsa4PvvPPO3JLvKd4BCfx9Aijof58dfvIfInBsw7HQEa8OmKNn5T+tUo3kSdpPHZ98rOfgJYP3w4oV0t4De58OPn4h+k5dCWdUIj+dk+HTrYfBLZWBfNHtrt28xktTt3266h/qbqm6zYDmb75x9qdj0xghgdXbNVg7YeuHL1pR0Ec+O7RW8tofvwlVbeHZeq6n55ShkY/1f+oTfpzMoTGjXqQnz011aEJZxaWQk6ezYMNPJ+CM5gDFcQcLqRj62/AZg/vXCqy/Cvetl6rhjZ25hgAKOg6JUk2Ar5vP6Tft4Q3z1swLZLSCh6jpze9v8ZreKmzbyJEj9dSZ01pd3HNwThWZNpDclwVqD4L9v8iwZOsRcItlIFfyeGo3q/by1IQlK0q1oSXUuUEt33gzfXdaLBASeE+HhpvGfz/9WUKsUe61MNKh3QbV3bd+1+ZQVbo7R8tVek0ZNvix/k/OMgQ9esxrtpPnJtldahhlItFYAOw6chE27j8Fl+2hkKPla11ef3RL77F9XyPlg3A9vYTGKjZ78wRQ0G+eIbZQggSOrj0aMiQyapZ2Lr+bJIhMDLUverrPGwOfGfmMzPbtc+xZtCgmLNfzqpSXb5dEBXQpkBxOV9jSLUfALZSBXJtHrtWs+uuxCYuXlWA3S23TA1t07/5L8rFYRlhgzY6Nt4z7YloXciex3NTxsC7D6u/dlLApWLNVuqzlqn2mDnv/0X6PT+Nb2g+OHf2W7cSZiYEezUE1XnTABi4aBsu/3wO7L2Qye1A5cAY6c2OXTX/u3s5NN5ZaZ2PHLE8ABd3yQ6B0A/iob0ztLxd/uTbILd7j1uW86o3qvDDjp4Vf8V6fXTCnyaVvEj6vIgbV8GgeKgtuYDYHpJxS4HOfoOfZ3EqtZjVenZLw6eel29KS6d2g5m++deantKlc0Gt0bLI1ekVsFyucrnYtzeFdhzRJ3pi4waHZKlxW89V+sUOHPxL5WKwh6KNHvCWlnZ4YohGHAIQIxA55KoGk45mwac9p5iHB5Lz6mxYV/faorsNeHlsynsJWkcDNE0BBv3mG2EIJEoh+bnibr5evX3sHDb5DD6Dpr7/dp+Pjo55JZ3FxQsrhA8+VOXJqTjlVcFwAJ7AQARQhgKSdUNjyLUfAQ40pd/me5lVfjt3+WVwJdrPUNn21oDfeGr1imjUF/dnBjZLX7djEBT1Hy1f7xgx97/GoR3mErh8dPeJN6eSZySGq7tBlle+VByYKcOiCDqt+TIU8EgK/KZfZk72fWdxr1qDXSq2zsWOWJ4CCbvkhULoBDOnc78F93yUvDwV7cL7gPrJo09o2d3W6Ky85OVkK+OSTQRG/5owIVJj9UgADp00hghTMUk8osHTrYVBoGciT3Er1ZtVfiU1csrx0W1oyvRvUsnvP9N2pU4BAgBGhr4x9itxBckrmbqW31WHd3mm8Z/3OjUG6rcJlLV/rM2nw8CcGPDHFJ+iv246nT3GoqgM0jdcLBEY1OJ3vgAXxP4MT7oA8QWHNn2r71Xsroh8vvVZiz6xOAAXd6iOglNsfdV/3rocSf/60PAkOzLfJKfPj57ao2LlRPktOllI/+WTYv37Nes+ugXgpgIBCZSKJIezoKRkW/3AYFCEM8kXZU61ZxEvTEpeuLOWmlkj3BrZ8q8cvu1OmMAKBNTs1/WHcqqld/4lKcSVizE00Orzb4Aa7v0rcHKLZKmZrTq1fzLBRj0Y9OglWrNAOHT78RuCJU5MdqhoMmkpEQkGnCknPdbCF6/aBG+6Ei5AH9R9o+sOEb2Z2uolu4EeRQIkSQEEvUbzY+M0SGNSh93OHfkj+uLwQLOWQ3NT5X61rXrFzRUPQj3766fsVLmQNoYxKOTYAymRipw52MF2GRT8eBk0IA7cge6q1iHh16valX9xsX/zx82+3fKsHj9CNpLhOjX8Yt2qaNQW96+B6yRu3fR2s2f51WXVqfaYMG/FY1BMxfA09ZfTot8QTJycEK2ow6KoRoatUJqcvO9jC9ftAhUqQCXlQr3PDbdGbZ3bwx3GAfbYGARR0a/jZb63s37H3qylb984rTwOFLMhJXfTdJy3u6lQ/j23ZIh5Z+eWIMpmZQwhQIV8kEKh4iIMGs32/eGDuj4eACWEgCx5PtRbVX5iyfclqv4VwEx3nhWXSd6fEAoEgI8t91bSnSVmSfRNN+uVHeYT+81dbvw5W7RVyNJfWd+p7Ix/pZwi6emTUqNekk6djQhQlhAs6P/CN2QDSc4LZnC93gwaVIAfyoO5D9bZHb5zdzi8BYKctQQAF3RJu9l8je7bt/vqJhIMfVRAcNIdlnVzw3aeNCwT98OovR4VkZr/NT17xEAbBsoc4SDDb+4sMsxIOAoihoBJZjmhZ9RWrRui+fejToGDK/dupXQkp+cNZStuIG95lYKOfNyVuCtWkCtmqU+sb+8G4h/s9PgEAlCMjRrwqnf4lxqEqoUTTjCNkVKKRc+4ybO7q3aBARciGXKj/UP1d0Rtnty5ttmF/kEABARR0HAulmkDftj1eO554aF4ZsNF8KS9tXnx804Ip99SPl44PzczsxwBEHRiIugySGAz7zygw68eDIIhlgTGPUqVV1VdiLJsU99abZ3anTuNT7rXub5I4dnXskySUZJRqp5dA50Z2e6fx7vU7NwarYoXLWq4eOW3oxIciu/AtaPLREWNfF09xQfcEE91DBP5bUddJuqcMm/XlblDJXZDP8qHxQw2TP9g4q2UJdA+bRAK3hAAK+i3BiI2UFIH+7fu8npK4d26obhPzJXf6ok1r6/Msd76GfmzhskmhWZf6AgGBaPzYTBmILRgOpcsw+8ejIAhluKDLEa2rvjJ5+2JLrqH3b/7G6+d+Ov7hFUFfGfuPHJ9aUuPh77bL96Hv3ZC0MUgV78rRc/X+096b0rnv46MAwHN4xPg37SfTJwZpnhCiu7mgE6rp7JRcBmZ+uRsUWh7cej40ebDJT+9vmtni7/YBP4cESpoACnpJE8b2b4pAv3a93jyWtH9OqG4TnJL7+Pz4NY0LIvS0j5dNCsnMiARgtEDQqS0YeFJcIUH3RLSMeHlykjVLv/Zr9torv/58YpY3y71J4rgVsY9acdva8G5Dmu1Zn/SVwyfo/aYNnfJQ3ycNQU8dMf514WT6ZBT0m3pU8cOlgAAKeilwAnbhzwn0a9/rrWOJ+2eH6nzK3X1q0ab5DY019ORkKe3jZRNCMi71K4jQdSITwRZsZLn/LuhuT3iLqi9M2WHNpLioRq+9eH7/iTmMJ8XxLPfl056wYulXb4SeuClIle7kEXq/qUOnPNTPK+iHR0W/Yj/+S0yQ5g4riNCJpsNpuQwrHKE3frDpz8M3zWiOzysSKK0EUNBLq2ewXwaBqHa9u6cm7ZsTpttJnuQ6uSB+TaOCCP34omXRIVkZUQyMEzXgzwS9Sssqz8ckLV1jRaT9G77y3NkDp+bxLPd7OjbePP67ad2seDgLLyyzf/2ujQ5VrMAFPWra0EkP9n2Sr6F7jo4Y/6J48sy0YM0dCr4p9yIFvXPjn4dvnoWCbsUHyU9sRkH3E0dZtZtR7Xu/kZq4j6+hC/mS+/i8lasaVXqskpNvW0uLWzs6JDvrbfAJetFr6G6HeoxLAAAgAElEQVRPePMqL03Zac3CMv3qv/LM+UOnFgAhQdXbN1g3YcuHz1vx+FRj29q6pM3BmlTRiNBjh8Q8FNlltCHoo6JfEo+fmerQPNeP0Ds3Th6+GZPirPq7yB/sRkH3By9ZuI9RHXp3T92+j0+5C07JdeqjlavrG4LOmJDWe+DokOzMd24g6O4qrao9F5O4eK0VMUY2fPnpXw+cXgAEgqu1a7B64tYZr1hR0Id1fbvhvq92bw7WvBF6v9ghPMt9HGzdKh/5IfF5LujBmqfsdafcOzfZPXzzzFZWHEdos38QQEH3Dz9ZtpcFgh6m22me5Eybt3J1k4IIPTVu7egwQ9C9We445f7HYdK3wavdLhw8OZ8LevV2DVZM2DrjNUKI22oD6toIPSp26PgHn35yPPwLPEdGjHteOvkLj9DvuEbQeZY7U2h54tbzWWMUdKsNG7+zFwXd71xmrQ5fK+jj5n/UtNHLjfL5lPuxuLVjQ7OzBhaO0P+Y5e6WI1pFvDA58bNV1iLntTay/ktP/XoofREX9HvaNlw5/ocPuaC7rMZiZNdhDXdvSNgUrBpT7lpU7NBRD3Z5cgpUBiV1xPjn6Mn0Gwp6o86Nkz/AKXerDR2/shcF3a/cZb3O+gR9VphuF/Il14kr29YYE4/1GTQ+NCujPwC7sg+9KEEPbxH+/JQdyyxZ+rVfg5efOH/w9MdASOg97RvGj98y/UVCiNNqI2l418H1fv4q6RvfGroWNfW9Dx586vFpkLRCTjmY+j/x1NnYINVdDiN0q40Mc9mLgm4uf5rOmv4d+7yZkrDX2IeeL3mOzVu58sqU+7EV68aFZl4aWLiwTJGC3qrai1MSF1vytLXIJi88fXb/mQUEILhGu4bxE7bMsKagX50Up/WbOmzoQ/2emMlruR8dMe4F8dTZyY4/CnrBtjVjyr3Rfxvt+uBrLP1qul8yJjIIBd1EzjSjKQM69u15NGHPDN8a+vEF8V82ubJtbeHSccHZmXzKnReWITqRoYh96HKVllWes+q2tdfqd3v2bFr6fAokqE7rBhunbpn3rBUj9GFd3q6/b+Ourwsi9H6xQ995KPLJOVzQD30w5gXbqXNFJcVdtYbe6L+Nd3zw9az7zPicoU3mIICCbg4/mtaKwoKeL7mOfbRyVVMjKS45WTq+aNnY4EIR+p8kxcnhraq8OMXPz0NnjF31rBJC2I2czj8T1fz1l9MOps4ABoGNWjf5OnrNjOdIOXL5Rp/lPy+4Z3HuVZz2buc1w7sMbvTzpqSNBWvokZOHRD48oMtCXrU9ZcT4l4WT6VOK2LZ2laA3/L+GiSO+ndP2dtqB90YC1yOAgo7jo1QTiGrfu0dq4r6Z3gjdlTpy5YxmzR9r7t2HviJ+TEhWJt+HLlCNgVZ0pTg5vEXV5/2xUtxHb30krd/6xf/9dvpCl0AiBRFdoAR0YJQJOiE6YUCoThRCBDfoVFcokamkg6ZrTFdUUt5WJjg/P79hOXpHQ0IovahlZNlDg/Zc8mR7qE2QA5hIBRlEQSMUBN6QRjWqiZSBRIDowAgwQhRGdPdl2SXUbFDr5Ot9X4p98M1n0kr1oCmic8OfHdzo5/gdm/jhLDn6ZaXvlMFvPBLVdTmsWMGOHj72knj8l+kO1RVCmAd4LXdfYZkCQTdquTf8T4OEEd/Nbe9vtmN/rUMABd06vvZLS/u37/NWSuLeWb7CMifWbYlrQO4Ld/F96Md6DRoVlpPx7g0qxfntlHvX+g80zzqftTRQtVcTZYFKugCEESAABBgABQ0oE/j7DGj8yE9BB4UfUCMRoIwC9QAE6UEAGo/uCU8dhFzBydQABoToQDyMBGo2kDTK43HQqQoa1YHru/c+/MAbvh1QBw+VQQsGT4v7W00d/fm04YQ34Edfw54d1nR//PYN3lrul+XImMGvPdy/axw3gZd+tR3/JfZGgt7g/gY/jvx+bkc/Mhu7ajECKOgWc7i/mTugU9/eR7ft+dBby92VNnLlzMbXROjvAo8jfWvoPCnuULrMCtVy91tBf/fpHv85sPPwkrxz2RUcgoNImgDUK+hcf40/ufAa0k4AVKqBKmigEwX4X4RAEARqdpCYDSgwcBMF8iU3XOa71ngYrokQpIggMgF4qxpooFIGXN95u5Qx43OMaKDbGajBVO3wSMdFgxdF9yGEaP40lvjhLHvXJ27w1XJX+k5558VHop5eDVu3ktQtSa9RPuWuuoOvF6HX71T/h1FbPurkT3ZjX61FAAXdWv72O2sHdujT68j2vTN8h7OcmLdyVcNCpV/H8tKvBBjlUWjRSXEeObxVVb/Mct+8eLNjxzff3w9u1ipECgqRiGgTBUlk/LhYwrWWMaZRVdM1XdN0VSGarlJZ0WS3RhRdC1SC6IkDx1pcPPPbfQKAWL5axXNVG977WaaW6WY2wRYo2kSbbBdsQAUCgqBSHVSqcxUHIITagAiib+1eFVQihgVe6tC18xcNH2h+wN8G0ntd322+f8OOr7yCflnuEzPkxUf7P2XU9z8yYvyr0qn0WIdiCLpxHnpRU+4N7q+/deT3H93vb7Zjf61DAAXdOr72S0v7d+jTK+WKoLt4pbjGvwv6uokh2cY+dB6h/0mlOP8V9GsdVigxjj+3VyXF/Vni2sBmL3U7c+T0R6BDSIP7Gnw9fOXM5//q8almSI4b/tSQFns2Jq13qKIh6JGTB7/88ICuRrGhIyPGviqePDc9WHUFXV/QccrdL3+JWKjTKOgWcrY/muorLMP3odN8yXNy3sqVDa6Ufl0RPyksK7Nf4Qj9j1PuHrlyi/BXpu5Y+oU/2n+zfe7V+Nluxw8fXyAAOBq1b7J5/Bfzip3lfrP3Lk2fH/7skBZ74hO/cqhS+Rw9V+k3ZfDLD0U9tRJWrIAjB1NetZ06+2GQ4g4sLOjpchk248vdoNDyRlJc/f80+GHUd3Nxyr00ORb7chUBFHQcEKWaQL92vXoeS9o/0zflnj4ufm69Rp29pV/T4q6K0I0p96LW0K1c+rVPq+dfStt3cpbAmKNB+2ZbJiyf+VRxt62V6oHxFzs3vOvQVns2JK5zqKIh6L0mvfvG4wO7fs5nOg5/EP2qdOqXD4uI0K86Dx0F/S9Cx8v/cQIo6P84crzhXyEQ1aF379Tt+3xJcW5+HnpDo7CMt5b7eF7LnW+uKlhDL0LQlbubh78Su3PZ8r9yX7NcG9n8zZeP7z82mzIa2KB94x+il099gtxJcs1iX3HtuHoNPVftG/Nuz0f6d13M96FzQbedPjPLobjt15tyR0EvLm287nYRQEG/XeTxvsUiUDhC58enzo//skGBoKetiJ8QkpU5oLCg/7FSnEeu0qLKyzE7PjO2KFntq3/jnq+m7UuZSYEENmzfdOuYrZOfJMR6gs6z3PesT/rKu20tV+sz+d0Bjw7oOhcAtCMfjHlJPHVufrDqlnAN3WpPiLnsRUE3lz9NZ01U+549UhMP8H3oNF9ynxk3f25d47Q1vg+994BxodlZ71w/QvfI4c3DX5yyc6kla7kPbNq9e/qetGlAIKBWh8ZbxsVN7WLFCJ0XltkTv2OzT9D1vpMGv/1Ih6dmQTNQD78/+nkp/ddPglWXcF1B/0+DbSO/m9vBdA8ZGmQaAijopnGlOQ2JbN+zR9rvgn5+XPzcWsYaelyckLZlx5iQ7IzBN9qH7q+V4m6FR/s3f/Otsz8di+WCXqND42+i46Z1s6Kgj+w2rHHy+u2brgh6zDtvP9LuaUPQj7w/7kUp/ewix40EHbet3YohiW2UIAEU9BKEi03fPIGodr37pibtm8YjdKfkPj8/fk0tY8o9Lk44/v2O6OCcDL4P/coaOh7OcjXz/s27v37up9QPGYHAGh0afRv9/fSuhJC8m/eMf7UwrNs7jfev33VF0CMnv/vOwwO6ek9b+2DMc+Lp8584FJd4vQi93v31t47Gfej+5XiL9RYF3WIO9zdzr06Kc51dtCm+zl2d7srjU+5pfQZOxDX063u0f7M33jz7c9p0b4Te6PvouOlPWTFC54K+b/2ujd5a7rl65JQhAx+O6mKctuaN0H/52KG66XWT4jrV3zJqy0f/8bdnCPtrHQIo6NbxtV9a2q9dr8hjSft5hE6ckvu3T9YsrlXu4ZqXjSz3L+JjQnOMfehGvfKit61ZOyluULPuPdN/Tp0KBOw172+6bVzc1Cf+amEZvxw413TaF6FvdBQh6Ic+GPNKwOnzC4MUFxd0fjiLUSnuD/vQUdDNMBRMbQMKuqnd6//G9e/QKzJlu1fQ80V31qzVy2tEPBqRZQj6ivgpoVmZkSjof+7nQS27R57ZnTqJEbDde3/T7WPipj5mRUEf3nVIkz0bkrig8yx3vW/M4Lcf6f/ULCNCHz62t5R+7kPHNYJ+Wi5z1T70uh3rfTdm67wH/P+pQgvMSgAF3ayeNYld/Tv0GpiyfX9MqG6DfNGdPXfDopqV/69OhrewzNppIdlZva8v6G4lvFnEq1N2fcaLiFju652WPSJP7z5aIOg7x3wz9UFCinceuplgXXN8KouaOvSdB/t1mQUXL0pH5348RTx2srtDdZPCEfofBL1D3W/H/DD/v2bigraYiwAKurn8aTpr+nfs0z8lYe9Un6DnzNv8Sc1Kne695IvQY0K9pV+vM+XuVqq0rPJaTNLSZaaDUwyDCgt6rU5NEsauiH3cihH6sGffrr9v7a6vgzWpIo/QB8x8//3/vvnYz+C8XPHU4uUj1V17qhYh6Fedh163U71vxmyZ17kY2PESJHBbCKCg3xbseNPiEujfrk//lCSfoEvuvAXxS2pU7FzjN+8+9EGxodkZfW8k6FVbRLw+acdnS4t7TzNdN6hVj6gzu45O4FPute5vkjh2RexjpCzJNpONxbHl/WffrbNn7Y5vgjWpEhf0d+eNju70Suem4Mmvkr50ZQ15246AGwo6RujFQY3X3EYCKOi3ET7e+sYEfNvWeOlXfpa3a+FXS6tX+L/qF/i2tWNbk/ga+h8i9MPpMpv141EQhDKEMTcvLPPmlJ3Lltz4bua74p3WPQec3nkk+oqgr4x9nJQhWeaz9PoWDe02tO7+ddu/9Ubol9nQheNj27/4nw4g5997ZtmqIM8PSfSGgt6x7tdjts5/0Grs0F7/IYCC7j++smRPB3TsHXU0YR+fcif5ktvz8dqF1e98sO55I0LvM2hqaFbG9ZLiDEGv2jri9UnbrRqh9xx0ZteRsYag8yn3VbFPWlHQRz47rP7utQm+KffLbOzK6d80e6zNfeDJDz6zfDV4tibCDQW9Q91NY36Y/7AlH0Q02i8IoKD7hZus28mrstwlt2vRpmX33NWp2q88Qk/dkjgpLDuL13L/0zV0YG6lSquqPSYnLvnEihTfbtOr/+kdh6P5tjVD0FfGPmHFKffhXQfX2/NV0tcOTfrXZf0yG7tmxu6mD7VqArJTPLN8FUFBt+LTYT6bUdDN51NTWeRLipvii9DzZmxaVqsaF/QtW8TUFfGxYVmZfW64ht6q6puTEpdYcsr97TZv9U/fkTKeR+g172+ybdyK2CetKOjDugyrv3/jts0OzfYvvoY+IX7m7kb/bdES5HxynQj9qm1rdTrU2Tz2hwUPmeoBQ2NMRQAF3VTuNJ8xUR369EvdvjfWJ+jOmWsX1qzKp9y9x6d+GJqd1fM6gm5MuVdpXuWNmJ1LPzMfnRtb9Hbrnv1O7zwywYjQeVKcRdfQeYT+81dJPCmuIo/Qx6yecazZI61qgqf4gl63Q53NY1DQbzzo8IrbRgAF/bahxxsXh8A1a+jOj9curOlbQxeP9Rk4IzQrs8eNIvTwFhGvT7FoljsX9PSdRyb6IvSkcXGxj1hx25p3yj3xa2+EfhlGx8W6Wjzx74BCgk4cqhuuvw8dBb04zyxec/sIoKDfPvZ452IQGNCh94Cj2/fxwjI8KS53QfySmsa2tS1bxOMr1s0Mzsp464aCjoVljMIyNTs13T7u26mPWrGwDM9yP7Bu+zd8DZ0L+qjlU1nLJ9sSkPPBN+V+Y0HvWG/zmK3zcMq9GM8tXnJ7CKCg3x7ueNdiEigcoTtFd+acDYvuNSrFMSYe6zNoZmgxBN3KhWXebvFWv9PJKZOAgGSsoX8T+7glBb3roNr7v9rNp9zv5oI+cvlUaPVkW/hLgo5Z7sV8avGy20UABf12kcf7FovA1VnuroxZq+bVini0YZZx2lrvAdNvXPrVI0e0CH9l8o6lXxTrhia76O1Wb/VP3+VLiuvU+IdxcdOeJOWsV/p15LNDa+1eu/17b2GZ4gn6tYez1MV96CZ7OsxnDgq6+XxqKot8x6fO8B3Okr3ki9nVy3Zpku07PnVySFZmfwAG5DqnrVlZ0Ae17jkgfeeR8TxCr9GpyffRy2MteXzqyK5Da+/+yigsU2xBL6KWO+5DN9VvF/MZg4JuPp+ayqLCx6fy09ZmfDH7nmrXCHrhNXTBFswOpssw21spDhizeITeusfA9J1Hx/E19BodGn8T/f20roSQfFMNkmIYw0u/7l27g6+hF1vQr43Q67Svs3HsjwseKcbt8BIkcFsIoKDfFux40+ISuCpCl1wZi9cvrXHHA/fk+CL0iSFZmQMAGCmI0IsS9Motwl+ZatEp93fb9Hrn1I7Doxkh0j3tGsaP3zr9RUKIs7j8zXLd1fvQizflfm2EXrt97fXjflz4uFmYoB3mI4CCbj6fmsqi/u16RaYkec9Dd0ru3z5d/9m9hqDzwjJx66aFZWdcdXzqHwXduqetMcbIkPZ9h57YfvADACJWbVv/i0k/zHidEOIx1SAphjHDuwxu9POmpI3BqlHL/W8lxdVpV+ersdsWPFaM2+ElSOC2EEBBvy3Y8abFJeA7nGW6b9vapenrP6t5j0/Q0+LWTQvJzuh9/TV0t1KledVXYnYuWV7ce5rlOsYYHdq2z3vHkw69BwBC1fvqLZ60bVYvQohsFhuLa8fIbsMaJ6/fvtGhihVQ0ItLDa/zNwIo6P7mMYv1d0DHvlFHE/YUHM7y66drFtcu93DNy0aE/kX89LCczF43WkOv3KzyS1N3LVthMXTABf29jpEjj207MBgAaMR9dT6evG1OH0KIYjUWw7sOabJnQ9KG4go61XR2Si7Dz0MHhZYHt54PtdvXWT/uxwU45W61weNH9qKg+5GzrNjVaw5nObto0/w6d3Wqn8cFPe2L+NiQnEw+5U7Al+Ve5JR78/BXYnYus2SEPqxDv1FpCfvfNQS9TZ2FkxPmRFpX0BM3OFTp6ghdccKZZauI5wfjtLXrVoqr06722rHbFnax4nOINvsHARR0//CTZXsZ1a5n39SkAwVT7umLNs2vf0XQfVPuN4rQw5uHvzhl59KVVoPII/RhHaLGHU/YN4gBkCqt68yL2T6nPwr6VWvo5Mznq6E4gn5v29proxNQ0K32HPmTvSjo/uQtC/Y1qn3PHqmJB2aF6jaaL7nTF8SvqVexc8V8b2GZQTEh2Rn9rh+he2QrT7kP7RA5+njCgXcIAAlvXXduzPbZg6wr6EVMuf+FCP3etvd+GZ2w6CkLPoZosp8QQEH3E0dZtZv9O/TslbL9wExvlrvrl7HxH9Vp1LmRIeipvQfw89B5YRm+bQ10IpMiptzliOYRL0ze+dkqqzHkjIa2jxxxPGH/YABCqrSuPTsmce47KOh/L8v93n/XWhO9/eOuVhtHaK//EEBB9x9fWbKnke169kpLOsArxVGn5Pnlo5Ura1d6rJKTi9Wx3oMmhGZnDLzBGroc3ryKZafch7aNfO9E4oFhDEAIb1VnzpSkOTxCV602mLxZ7gkb/7CGzs9DL+6UOwq61YaN39mLgu53LrNWh/u169XzWNJ+PuXOT1s7MW/lqoYFgp7aa+DksJzMqOsXluH70Ku9FpO0eJm1yIGR5V6wbY0woJVb1/loSuKcgZaM0J8d3GhPPJ9y9562Vuhwlr8g6DVXR2//5GmrjSO0138IoKD7j68s2dPCEXq+5D6zIH5NXWMNfcsW8Vjcuqmh2Rl9bjDlrlRpVfWVmESL7kNvH/ne8e0H3iMANLxN7XkxCXMHWFLQjcIyOzYF/3EfepGCXtS2tXv/XXtN9PaFOOVuyd9E/mE0Crp/+MmyvbxG0H9PiuOC/sXamNCcrL4AjF5nDd3Sgj6sfd/307YfHMYj9PD7rC7o11SK69IWwFP0lHvRgn7vyujti56x7MOIhpd6Aijopd5F1u5g//a9+qQk7ufb1niW++9T7nwf+or4SSFZN55yj2gZ8erkpM8+txpJ77Y1vg/9wDuEMRp+X915MQmzLRmhD+s6rOG+DQmbrir9ioJutUfC9PaioJvexf5toO9wlg99gn5y5MoZDZo/1tzpnXKPjw7NNg5nEf78cBa3tSN0X2EZvm2tSus6CyZ796FbrvTrdQ5n+QtT7veuiN6+6Fn/fqKw92YmgIJuZu+awLbIDj17pW33ZrnnS+7UOVvimoTfF+7yZrkPHB+anTkQp9yLdjRnNKxD1Ni0hH0DeaW4qm3qfjIpYTYv/Wo5QR/adWjt/UWfh178pLj77l0VnbiomwkeKzTBpARQ0E3qWLOY1a9tr7eO7dg/u2DKfc6SuAbhz3gFPa3PwLEhmRlvAwEeoQMjMlBbMFx7HnrllhEvTU1aYs1a7h0iRx9LOPCOIej31V88advM3lY8bW1kt6F1k9clfOPQbDeR5X7v6ujtizDL3Sy/XExoBwq6CZ1qJpN829Z4YRmaJ7lPzC0s6L0HjAvJznqnIMv9zwS9SosqL8fs+CzOTFyKY4sRoXfsNzpt2/63vYez1Pt48rZZvJa79Y5P7Tq43p6vEr8urqAXmRTXtvaa6ATMci/O2MNrbg8BFPTbwx3vWkwCBRF6mG4n+ZLr+OwlcY0KIvRjvQeMC83KHFQQofNKcdQWzA6lyzD7x6MgCGWAMbcS0bL6q5OTPrViUpwwrH2/D9K280pxIPim3PtaUdCHdhta98BNR+i4ba2Yjy1edpsIoKDfJvB42+IRKIjQuaDnSa4Tc7bENTTW0HmW+8p1E0MyM3hhGWPbGo/QiS0YrhV0S+9D7xg18vg2ftoaE6q0rrsoZvtsHqFbbg3dO+W+/WuHJlX6+4VlUNCL99TiVbeLAAr67SKP9y0WgcJr6E7JzSP0hkaEbmS5r50cmm3sQ7/OGrpbqdoi4vVJOz5bWqwbmugiPuX+XofI4ccSDgwlDEh4mzrzY7bP4dvWLCfoPMt938aEr4M1qWJxBL2oKfda/661ZjzWcjfRE2I+U1DQzedTU1nkO5xlhi9C51nuTQsi9NS4dRPDsjP6FQh60YezeOQqrSJetWilOOG99pEjjm0/8C4woFVa1Z4bkzTXmqetdRvcYM+6xE1XraH/xX3ote6rFTc+8eP/meoBQ2NMRQAF3VTuNJ8xke179khLPDAzTLfTPMmVOmdJXNOCCD1tRfwEY8rdm+VOdCJDEaetWXkfujC0Q+Tw4wkHhnBBD29de9aUxLnvWrL0q5EUl7T5Zqbca9xX84uJiZ88Z76nDC0yCwEUdLN40qR2FE6Ky5Nch+csiWt5Zcp9xbpxoZmXBnJBpxoDrcjjUz1yROuqr0zevvgLkyL6U7OM41MLBJ3Xcm9ea8aUnfMGW/G0Nb4P/cBXxhp65StT7teJ0Immw2m5DJv55W5QaHlw6/lQq03Nz8cnffKC1cYR2us/BFDQ/cdXluxpZLue3dOSDsz2Regpc7bENbtSWKbPoOgCQb9OUpy7aouqL07asWS11QAa29ba937vxPaDwzRgJLxl7RlTk+YPsaKgv9/l3Tp7NiZ9HazZ7r56Dd0JZ5avAs/WROJQ3UCYBwReVq8IQa95X824CYmf4JS71R4kP7IXBd2PnGXFrka16909NWnfHO+2NefR+E2fNyOdqrl9We6jQzIuvVO4sEwRWe6eKm2qPR+TsHiN1fhxQR/crteQk4kH32dEp3e3rDV1WuLHw60o6CO7RdbdvW7v5j8Kej6cWb66mIJeK24CrqFb7THyK3tR0P3KXdbrbFT7nj1S/2wNPW7dxJDsG25b80S0rPryZItWinunfe9BpxIPjaJM51PuU2J2LhxhSUHv8nb9nzbu3BxUuFLcX5xyxwjder9//M1iFHR/85jF+uuL0Aum3FNHrpzRrOBwFp4UF5qV2Y8BE33HpxqlX6/Zh+6p2jLiuUlJn31pMXTAT1sb0qZv/xM7j4wRQBbDm9aaOil5oSUj9NHdBjTYvS55000Jeusayybs+PRFq40jtNd/CKCg+4+vLNnTqHY9u6deWUN3psxbubp5pccqOVlysnRs4bJJoVmX+haqFFeUoLurtqr28qTExSutBpAL+vttevc/tePQGA1AqNysxqTJyQtHEUI0q7EY3m1Agz3rdm9yaPbfa7n/xQi9eusaSyfv+PQlq7FDe/2HAAq6//jKkj2N6tC7e+r2fb4I3Zk2b+XqJgWCnrbo84lc0G8Qoburtrzn+UlJn1gxQicftOkReXLH4WiFUPHu5jUmTdllTUEf/OzARgfW7d4UotgqFCfLvajCMtVb37N08o7FKOiW/E3kH0ajoPuHnyzby/7t+7yVkrh3li/L/ci8lataXhH0j5dNCMm41O8GSXFWznKng1q/3OvsnpPjVY1KdzepMWH67oVjrRihD+oWWTdlw77NDlm6OxfyYOTyKdDi8fsAZOfvx6dqHiC6GygwEBmwk+5QmB3/M7igLGjMDRGtqi2dvBMF3bK/jPzAcBR0P3CSlbvYv2OfN1MS9vIIXciTrp5yT1v0+eiQrIv8rG/pz7etudxVW1Z93qpr6P3+83qP/T/umngH3CHe3bjGuBm7F40nhOhWG1NjXnq3zu5VOzcHKmLl4gi6wABOuUNZYUGv2rLqZ5N2LXnZauzQXv8hgILuP76yZE+j2vd4IzXxIN+2RnMlV8VWfucAACAASURBVMrC+DUtK3aumO+r5T42LDtr4HWn3HW3M6JVxNOTkz7bdCsBMsb4s1Pw/LC/2Tb//N/97B9uSQi5qi2+ht6nw2uRZ5PTxgoeKlZuWiN6+q6F0X8WobORjMKIPzR7y/r3Nxn95Y9dy4E3EP3Y0No7v0naGKAJEX9b0FtXXTZpxxJMivvLHsEP/FMEUND/KdJ4n79FoN+/e71ybOf+eWG6XeQR+siVM5sXZLmnxq0dHZadOaggQuelX6/Nctd1d354i/Bnp+5ctuFvdaCID302dG7dLz774j1wsQoFPyY8p5wY4syAgQ4EqCH4DDRjSQC8UTHjJ8MBaAxAML7XiU4IkwC8YkyAUQa8KUOd+ZkqKgFQ+ZW+e1EGRCOE2XRNIwoV3LJAXOXCyh6vU7Pm7qr3VEtt8ch9Jxt1bpTP96G/3ebNQad3Hv5AZJJYuWmtCZN/mj+mKEHPTM4MG9Kz57CLp8+20JjIDdAI6LwbvCaNDsCM/jHeYyDcWIlQRgosJYzbZLyf6AwIN5LPWhNgxGsnMMaBGIwYT8oz7OHJeb6fE8ZtMmz0Xmp8ESCUXXnpYYKPEyWECbxfDIhOgOqEaQr3gUYYUwWiKzpjkiC6A4hEBY1S5lEDtFzl33ZBdFwR9MfaACiuIqfceYTOp9znxP/MCqbcq7Wu9vnEHYuxUtytepCwnVtOAAX9liPFBm8lgcj2PV9PSzww1zvl7kpdEL+m+ZUIfcW6cWGZGQMY0UX+q10H1Tg+9XC6B+b9cJhRoQzTmDsvolXVrpOSln53s/1KTk6WZnaP+d/JY8dHBGm2CJtMrghP4baveqi4DBKuSFf+tqiItzjPoa8l46WBUMbAxmVfEJmHcBVjmkiYE2yQnSHnZN1RqeJPVcv964B+ydk+7/yFxzy6DhWb14yZuHs+34d+VZY7j+SHdY16IWndd9NDmC1UYEGEGe8jPk29WXAl+XlGDbKcB6es8TcLgQEVbUxVNbCBBFTTWACzEY3pwCRGuKCPXjYVmj/exruGvnw1uH9MhGC+hq65gXLrGYFT7hCYE58MbijLFKZARMuqi2N2LX61JM3BtpHAzRAozi+Sm2kfP4sEboqAT9D5lLsRoS+I/7KFIejebWvRZbIyo3Qqi7xUp4sxEALLwbmzMsR9/xO7rFPmpFpGs8dbPzbwy7m7bqYjxzYcs8+fN+2ZQ18fHRXkcYSD7qGCIBNNV8GjqyCCBDroIPGAlHE59Eo4/1clFFQiFuv2RlhejCsp00ACl286QAIKASCBnelAwQMq06iu2QSiCJoqaLrTxoDKEY1rT5m4Z8HIawvL8HB71EtDum1bvXl2oEcKC2DBFIBPJPz+5Y3PS+GXb16Dizl/BdEJnyDhbzyUz5kYEyUinxbg/y0J4FLdoNkUWPbtCgit8y8AZxZJj1sOeXt/hjsIA0FxGXMjbkLZb+5gmLP6O3CTipBPJVanbYPp0T98yHM28AsJlEoCxfndUSo7jp2yBoGrBF10HVqwbk2bAkE/sXD5hNDMjEhNcIlUU0ENdEC+LAH1BELK6Ytw4vwl5qgYerFhg39NKBdaNpUEBCoyU4iqqmAPlBgFjU9nU6ASUUDUQdcZOBWiM1mQmC44PR7QbXfomYqorvwxscPJ0xf7SKqjjE2WaEUHQKW7KAQGBICiaUAIAd6uYMgKFxfdmETnDxiff9eLDub/4ETjJaAY4slnru3UmMIGVQfIy1chy+WCfEUDt5uCRyOggQCMiqBRAgrR3ZUb3jN5ZvLs0X+I0JOTJXBC+TkfzBit5rhfUPM8durNEbjyVTDh7h+jjq8UXNtT/ncUdAGgep1q8MAzj4AqqeD+5QT57cAeCNZUkGQ3SJrK3wZACQhjx847YdnmBFCkcDijKPp/n3l82LtxQyb5BwPspRUJoKBb0et+ZHP/dr1fTUna9xGfcs+XnCkLNy1odVen+nk8Qk9buGJCmYyMSE10ilRXwMlHsy0YBMUG1BYEGW4XBAeILNRz2akpuqLbg3RNoETXda5pTGAKXxsmOhFABx5BC4zqhICuUwEIlSEIMpw2fWPSYTUtIz/YzYgdqIfYJBkeaBoBLSLKgF0UQVFkoPwTXFwV1RB0YzXZeLp0EHTecvGgF/My4w5EcBgr8jpooIAGLk2FXFmH9AuXISU9E9IvuMGjBoIuBUIu6O4qLWrFfJQw/UphGZ5YKGdk1L2UfOShC6nH2wfSwNqSIFYRWL7AW71K0HncW9zOFcPUm8kmLCqTsPAvMiNCJ8Trg0J9sUs2yONjolwwXHbngyRRYIpM7AxA4osQigoBdjvIugCZSgDbe+wibNmbCtlCGGSIVHvstad795vbb34xzMNLkMBtIYCCfluw402LS6B/+z6vpyTu9U25uw4v2jS/jSHocXG2E1t+Gh96KTtS5xG6LkM+UcEe7AAl1w1MoOChKlCiQZjMI2g7qFQCRm08XgbQZRBAAR7HaoTHu/x6CkSyASECyB4J8txB8F3iKUg5L4NKAoAxD4j289CxfXVoEl4OwtweYKoGAp9iF7iyajy446lkoBHvn1zUiS4A/T3P689N933uT6TzqmeVGKvGAaDrXHhlIMQDlDLQBQJUCoY82QaH0i7A/qPpcD4rF9yC4K7VtP6H7y8dMwL27FHPq1ll81LPP59x4OigKrawimq+LAaEhYFKNaJrecaLSKEv4ovQC/SxBH9v3JrEfy7o3vmRgi4zYJobbIEi5LudIEgSCDoFQWVgZyJQlYBNCgT+iperAFPs/4KVW3bC8d/y4AJPAqxUJnfIhPcfafNim+3FHbt4HRL4pwmU4IP5T5uC9zMjgaj2vd9ITTQqxfki9PhWd3W6q0DQJ4ZeyumjU48o6DKoARQ8igxBhBoxq4u4wR5oAz1fAyCBQG0OcHt0EAkFEVSgzOMVdMpAM1aNRdBVHVRqh2xPIHyblAKp5zVQ9DIgAgEVzsGzbetBRAU3hAUGAFFELqvEJlAgug6aKgPXbUPQKTFEnbct6joIjIeAPoXnIsPn1Qt2vhlz7N6cNz5BwHxz7sSbEn7lWl/MyYyfM5GoEMAEIoDI136ZCkRTjQl/RWcgMwrU7oDMPBecOnMOVJ1pZe4qv/fu2pW/socGaIozr5nz19863gVSsF0TiS4IJI+/hNgISHKu8ZLi7YeRqW5079p+8e95H/k/vJ/X/b6QTXycGonz/JM+G/9uW9fyKegHBZ4sV7hfFFRKQZAE5nS5iCMgAIisgqQC2IkEugqgMn54KgAElSV7j7nYV7v2QTajkBUgqi2e6PjtqEmjXiARJMuMzxnaZA4CKOjm8KNprejfsferKQnGlLuYJxWK0A8etJ2YtXhiSEZOH0YUUdBV0G2iIeihogAe1QnMroNHV0CiwaDqNuJReBQWzEBTwc40YLoHCNVApQwUI69bALsQBJfcAqxP3A8Hzl0GhYaBrKlQyW6DJ/7dABpUECDAeQ5IYADJpQFAuTCpKvDoXDKmv3UjMUvjis2n4xkjIk+au7Lr7Aauus4T6ZVV4/8MeSRENDZuCZpARMaTv6h37xxfNZAIeJgGmgAgSTYANwPJLrEc52UGVANJJCSA6ynPE2AMNLsALv5iw/WMvyZcM73uX2vo3in3q5YMuD06Xx5RITjYAarbBQLfT6h7YfJFFrcogFMAOPFrNqz59jhzQ1m4LOpMqxicOfzDMT2admm6pqg97qZ9+NAwvyOAgu53LrNWh6Pa9ng5dcfB+X8QdJYspfX6fGJYZm5fRlWRqgwU0bv1O0DlkqeAKqigUb7lWSAMJGDMBoSJ/Jc4sxFGmO42Ur8UgYJCKMiCDXI8ImxO2A9HLuSBKoaAm7khLEiF/zavDU3uLgvlZDc4ZBmcgg65fPGV6ECZkfZmJMJRgYIsayBKNvB4NLDZ7aBrBJh+9Q43/slrHj7vkvsNstwL9q7x+4lqPrcKRCIZswuaznPrqZEIx6NRt3cbFyGiwESdAlVUsOk62PkyAKigEdVgpItcyFVgugK6LoDOQgu2h/+hO/yloqCLBS8YBSPy2q5f+/NimFdig5uTcag6iJpqLCYQI4nfy0vmOxFsAeC029iutBRIOpACWZfvAqcQCE5R1+p3arbs3cnv9bmr/l18LQK/kECpJYCCXmpdgx3jBHxJcbywDC/9enxB/JdNjCz3g3G2tNlJE8peutxHFTRJUAFkUQDGBAhSjIVrolIddOoBILKRuazztVL+j86A6ipIosD4djI3iOACEbJkCdZ+/zOkZ7pAEu9iOVo+CxE9+f/371pak5p3SDQvE2weAqG2cpCn5DHNpjBgKuN1UgRjx5QOlFCwUzsIxE5AAYmAGOgGvkD7x0ft2iiS28urqPB/b/TFk+IUKnFNAl33gKq6gRhr6bwkjA6CwG0VjTIwfBpaFRnYdQp2hYKkUpB1Hdw2AZwigEoJBIoi2PknNN1XQub3HtyaVe2r2/sz+wpeWG5k/1/9uRGgU+rNbeDQjD0OFBSZgFsT4LccJyQdSYG0ixchS2PAbKHMbSP6HeHlT30ev+YhUoOk/dV74vVI4J8mgIL+TxPH+/0lAr6kuILCMimLNs1vaSTFHYyzHZ+ZNCks43IfTZQFqmugCKIh6IGKUXyM8PVrnSogMrevaJt3VZVH1CIA0xiFXJmAEHIX/HIpH77eeRBO5QAoUijLURUt5I7gHR1b1ZvcrvndFyjJKisyFxH1QAI0hOmyooHo5jvdFCNb3shI0wlTVR3ydSargpTlhPs3frN1gEgkh3f9++qvIivMFEPMva0QkIkNKCFAqQdCHAJUrBAMd9/pgDuDbRCoq2DnW7EU1Zg9kEU+i8BnJwJAZw5wkUD41a3BiYwcOHcxC1y5Hu+LjrF2zqPYYnfkL/nz9l3MwEU1UCifGjEm24EpPI+RgselgyufQT4I4NIFUOwUcmx5Wt3mdb4fNmroO5XbVTuAU+23z3N45+ITQEEvPiu88jYQ6N+pz+sp2/b6BL1QYZmDB23HZ348KTQrp7exD11XQBa906gBsmhUBlX5AimfmtaNIqTe7WRE53PtIBAKbo2CLbgCpJ7Nhy07DsCJXCfItAz7BfLU8pXDvx4ybURkky5NTv0dszdPXuyYO33BxLwLud3LimVF3cNT5a4u/n7t1q2CSLg4UsqvlXjxFB59ax4AJoONanBnCIUG1StC02oVoVKwADbPZbAxBVQKfMIANCkEfnUS2Jd2Afad+g0u5Wrg0bnA2UCidmOvtkv3GHkA5vpiQKjuLaxrvAQZUxtGprsNbOBWNGCCjWk2O+SD5//bu/fwqqozj+Pv2vvccvEQIAERxBsgDBZ8yqVTB4rRjhfaeXx8ZkydmY5WHTAKAkVay0xnJh07ImBlVJRegIC2fdrgSKU6oyimFcEWg4ADYoIXIiCEhECSc0ly9t5rnnWSgGV6UVRMVr75R8Vc9vt5X/I7l7XXap18/Reeue1b02cVXlh4wC4HqrFZgEC3ubsW1Pa+09acZDhdvbii/M9HXjOyRZtAf/ix++KNDaWBmwqJbhPPBLpWEvFcpbQjmexK87CIjnSu0DabhgTarGzP+I6oWIHU1rfJC7+pkXeOJETCcTkaJIN+55+1btSEsfPm/7Ts7VMlrCyvjC1btOQ79bWHSnOD3KjrmVcNju/WYnY5MW+sdz3MyC5lM2vGzQOPwLzvbf49Gz0dydrxkOR4yprlbyoatKrslulujiMSc8yzTVe3qxxJy3mFEfmL0WfL8MH54not4niOBJE8qT58TH69a4+8czgp7SoqIlGtnJjZ/CbwAk+b7VHNQrr//2He8j95v7g/9GcdV3zSnnddj1c+zO+ck7/mD9w2936b33HqfC3DyCnJ90PZuxEyylPaDcRXGfNwSPu+p92o6xcMGljX/6wBL0+YdMnjX5l1y9PqLJU61f7zdQh8GgIf5i/Xp3F9/MxeLtAZ6OY89OzWryufWd75kvvOyJ5lK+8raKwr1ao1FGizR1s2NJV5Sd08U087EfEkT6KZiISjGdG6WZslUH5GxIkPlVf2Ncm6l3fJsYQSE/NeKAj8c9yqh8ofvvqcyWM+8u1JVZVVhU8/se7Ko/sahsck+6DC7GhjNp0329SZNDVBYzYr7fh7aFbOKafrMJMTYW5eI9aBWYyeyW7bal5D1+KEPcdVQZBXt//gBe/seecz0u7G+8f6xtqTx1Rfx1X5TlKuvuyzcu7gftl8PVSfkp8/tUHSkicN4kvfgoH6cKIhkwllUsNGjawdMHjoHi/wmiWqPXOYijlkJntt2Tf7zYEwSgdB4CilXGWW2OvAyx7KoswKw+znmZv4zbsa2Q17zGrEjvHNLkFTQcehK+brzS4+nTV3rFFTjupcq5j9wY7ZfzV744HW5o7BriCPiqiY1jpirqFjP9fjD3JOfM8Tf2eyB8Rkb44z27sb/Y4b8BzTC18FEsmNNI4cPWLzeSNHvjJi2AWb+o0ZfICX13v5L50eXD6B3oOb1xsu/c7LZpa+/uK2B83xqYlwqqZ8w6rPFU3qeIZe8/2V3ytoqCtVZstycwhHEBZH+0qcdnP+lqTdmGScmORlF72ntFn5LsqVtJsvu/cnZcO2t6Qu5UrCxFE0kpE+wfoJX5o4d/6Kez+2BVDZ+8jWiCPXnfSm9JoPtGW7yHXv6/Ka39Nx8/+3irNl19r4Uz+t/LOtv912U34mp0RSyZy466t42JfLpkyUaDgs//3CRkl6UWn2Xe2FY5l0rO3ZiydcvPyvrrtmy+DRFzYPiQzxZNxJW8T9qvM6LxWRX3X+fPPv5qPrv7suq+vP/9hgbhUl40RkV+f3HX3c5cTvol2d32B05z/ffFOJDBMJ7VUSOleZVovUiQQDtchBERkk4tQpaejcf35AkTQ0NqjCokJ19NhRpQOtAz/QhV7nKwwDik5cYZv4MkQy5uQ3grw3/Eaxu0YC3e7+9vjq5l1+x627fv3qQybQzdaviytWHX/JvWbZ6vv7NNTdqhzfzUhEhQKz0YvZFS4tIa2lTUWlzTVncrZInpMjbZmIeDmFsvO9hDy36TU5nGwVV/Kk0cm0xi8csuYrt31t3tRZU+t7Mtpz9z7XZ8XSpf+RqW/+B51pyu8TiBpU0EcikYjUHq6XRsfXyi3InDFkwC9uvu1rc4q/+aVDPblerh0BBE4IEOhMQ7cWuLN4xvTXN25f2nl86omtX3fujFQ/smpJn8Yj07UJdB3NBnpIZ8SVpESDQLe5IWl3zUlox8Tz8iUTHiq7D/rybFWNHGpOSE4kRye9dLrvsDN/OPGvpy64ecHNPTrMuxq549EdeY/cc983D7+9b26uVnlOJpPdNy0IhyXtKj8+dODa7zy4sPTsq85u7NbN5+IQQOBDCRDoH4qLTz7dAnOn3DFt96ZXzXvobjKcrllcUT6xa1Fc9bKVi+ON9bebe5DMs/FQEFYR3wR6QkcDT9odV8wdbCHVKq3RQbJlT5s8v6VWEu1hkVBYGt2m1Dljz3v02htu+JcvzvzikdNd2yf585aVLhv8s1Wrf9xP4pPdTGDWdksQdqRJpQ7eWHrTtTcu+cePdJzsJ3ntfG8EEDg1AQL91Nz4qtMk0BnoZi93JxFO716+bu3nshvLVFREqjduXBg/cmSmeYbernNURKKiMm0Scdt0yGuVjBuRlNlLTcXktX1JWb+1Vpra8sTTYd3iJlsGjBpYflXp3ywsmVFi3cvO5r37B6ff//n1Tz63qD3RerFZXJZTkPfeJX85aeFdN81foYqVORaODwQQsEiAQLeomTaWMqd4xvTqjdvNM3Rz29qeFc88OT57OEtVVbh69YpFfRobZory3TZz8lhaSd98cwBLk8SUksY2X0JFF8i21xvkly//Vlx9ptQHaa1yYkdGXTb2/qumFv9g8u1f/sir2buruwn18m+XD0k2NY3TgZtXeGbh9hFTR9SMHz/eLALjAwEELBMg0C1rqG3ldAa6eYaukuHUG+8/D736sRWL+tTXzXSyz9Cj4gRRMTevmduRzP7p7bECeaW2STZUHZBEolViblS3SFtz/zFDv3f9/Gn/WVxSzN7ctg0M9SDQiwUI9F7c/J5Q+uwpt0+r2bTDnIeuEuF0TXlF+cQis7GMrgrvnv3o4v4NB2e40u62S0SUFxM3FJPmTEZC0X6yqbpW1u94VxJBX8kLR/Sx1vqGcZePu/fyv71y+aRbrmnpCfVzjQgggMAHFSDQP6gUn/epCMz5wm3Tqze/ln2Gngin3lz5zPJx2b3cq6rCr6/+2b2F9ftnhVSr6wVRcXSOtPnm1rRcqdpZK8/ufEtaVKG0iqub/aPNYyeO/vdb5n7j4YtKLjIHbPGBAAIIWCVAoFvVTvuKmXvpHbfu3rjt4biOqZZI8o2VT35/4plXjk3qSh3a/cRd3+1Xf+DOqKSzL7n7Kl98t49s2lYtm6v3yrGgQBKhHO27rYkRkz9z57VXT/nJJXNL0vYpURECCCBw0mbLgCDQ3QTmTZ49bfdLOx7JCcWcptCxt5asXv7Zi0ouSujKytCOn6/9137N9XeZO8yToagc0XHZ/sZR2bS1RnTQXzcpkWSeOpY3IvZvFVuffESZs0X5QAABBCwV4Bm6pY21paxvTJp5XdVLVasKY31jR3XTm0srVo7P3odeUeFuf+mVr59x8NDdRZFo5F1f1Ka3G2Tz1r3i+vnihuPBQSd1dPiUi+dcMeeKx6dOndpmiwl1IIAAAr9PgEBnLrq1wOLr77608hfP/1fUjxRkwt5718/8u89/ddG0/WVlZU5JrOCroR1vL8lNq4IX9r6r/mf7HlGqQOeomE6p9sbCsef880OvPvajbl0gF4cAAgh8TAIE+scEybf5ZARWz106Ys3Kx5920uq8jOd78aL4A6WlN9xdXFaS2Ffxy8Etz+780avPb7l8c+3+cJN7hjT5QZAbDb014NyCb8+ecfO64bN4Zv7JdIbvigAC3U2AQO9uHeF6fkdA79SR+V+fcc//vrjz9rBEIkE4aI4P6vvo2CljKkf1K9KvPfXi7L1v1FziBXmRJolop6jvu8PGDJ+1YMOCp6BEAAEEepMAgd6but1Da/3NjyuHLJ63YFnySOqKaDgn1O5nAnH89jxHeV5rKjffiTmtKvD9/NzqIaNHfevGJTeuZze0HtpsLhsBBE5ZgEA/ZTq+8HQKPF72k/OfeGzNPYf2H746N5yfpxKeirhKojElh9N17dGi+NYJV036p7LVC188ndfFz0IAAQS6iwCB3l06wXX8SYFt5ZUFDzzwg6nplFccTjnnRzwvJ5bjHGiPtW0q/vtr1u7x9u8rKyvj1rQ/KcknIICAjQIEuo1d7QU1aa2dzjK1Ukr3gpIpEQEEEPijAgQ6A4IAAggggIAFAgS6BU2kBAQQQAABBAh0ZgABBBBAAAELBAh0C5pICQgggAACCBDozAACCCCAAAIWCBDoFjSREhBAAAEEECDQmQEEEEAAAQQsECDQLWgiJSCAAAIIIECgMwMIIIAAAghYIECgW9BESkAAAQQQQIBAZwYQQAABBBCwQIBAt6CJlIAAAggggACBzgwggAACCCBggQCBbkETKQEBBBBAAAECnRlAAAEEEEDAAgEC3YImUgICCCCAAAIEOjOAAAIIIICABQIEugVNpAQEEEAAAQQIdGYAAQQQQAABCwQIdAuaSAkIIIAAAggQ6MwAAggggAACFggQ6BY0kRIQQAABBBAg0JkBBBBAAAEELBAg0C1oIiUggAACCCBAoDMDCCCAAAIIWCBAoFvQREpAAAEEEECAQGcGEEAAAQQQsECAQLegiZSAAAIIIIAAgc4MIIAAAgggYIEAgW5BEykBAQQQQAABAp0ZQAABBBBAwAIBAt2CJlICAggggAACBDozgAACCCCAgAUCBLoFTaQEBBBAAAEECHRmAAEEEEAAAQsECHQLmkgJCCCAAAIIEOjMAAIIIIAAAhYIEOgWNJESEEAAAQQQINCZAQQQQAABBCwQINAtaCIlIIAAAgggQKAzAwgggAACCFggQKBb0ERKQAABBBBAgEBnBhBAAAEEELBAgEC3oImUgAACCCCAAIHODCCAAAIIIGCBAIFuQRMpAQEEEEAAAQKdGUAAAQQQQMACAQLdgiZSAgIIIIAAAgQ6M4AAAggggIAFAgS6BU2kBAQQQAABBAh0ZgABBBBAAAELBAh0C5pICQgggAACCBDozAACCCCAAAIWCBDoFjSREhBAAAEEECDQmQEEEEAAAQQsECDQLWgiJSCAAAIIIECgMwMIIIAAAghYIECgW9BESkAAAQQQQIBAZwYQQAABBBCwQIBAt6CJlIAAAggggACBzgwggAACCCBggQCBbkETKQEBBBBAAAECnRlAAAEEEEDAAgEC3YImUgICCCCAAAIEOjOAAAIIIICABQIEugVNpAQEEEAAAQQIdGYAAQQQQAABCwQIdAuaSAkIIIAAAggQ6MwAAggggAACFggQ6BY0kRIQQAABBBAg0JkBBBBAAAEELBAg0C1oIiUggAACCCBAoDMDCCCAAAIIWCBAoFvQREpAAAEEEECAQGcGEEAAAQQQsECAQLegiZSAAAIIIIAAgc4MIIAAAgggYIEAgW5BEykBAQQQQAABAp0ZQAABBBBAwAIBAt2CJlICAggggAACBDozgAACCCCAgAUCBLoFTaQEBBBAAAEECHRmAAEEEEAAAQsECHQLmkgJCCCAAAIIEOjMAAIIIIAAAhYIEOgWNJESEEAAAQQQINCZAQQQQAABBCwQINAtaCIlIIAAAgggQKAzAwgggAACCFggQKBb0ERKQAABBBBAgEBnBhBAAAEEELBAgEC3oImUgAACCCCAAIHODCCAAAIIIGCBAIFuQRMpAQEEEEAAAQKdGUAAAQQQQMACAQLdgiZSAgIIIIAAAgQ6M4AAAggggIAFAgS6BU2kBAQQgzgy1AAAAONJREFUQAABBAh0ZgABBBBAAAELBAh0C5pICQgggAACCBDozAACCCCAAAIWCBDoFjSREhBAAAEEECDQmQEEEEAAAQQsECDQLWgiJSCAAAIIIECgMwMIIIAAAghYIECgW9BESkAAAQQQQIBAZwYQQAABBBCwQIBAt6CJlIAAAggggACBzgwggAACCCBggQCBbkETKQEBBBBAAAECnRlAAAEEEEDAAgEC3YImUgICCCCAAAIEOjOAAAIIIICABQIEugVNpAQEEEAAAQQIdGYAAQQQQAABCwQIdAuaSAkIIIAAAgj8H7e6nzkGsVNDAAAAAElFTkSuQmCC"); // Reemplaza "base64String" con el valor adecuado.

		//when(servicioImagenMock.getImagenesSecundarias()).thenReturn(Collections.singletonList(imagenLogoMock));
		//when(servicioImagenMock.ObtenerImagenLogo(anyList())).thenReturn(imagenLogoMock);


		MvcResult result = this.mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
		assert modelAndView != null;
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("datosLogin").toString(), containsString("com.tallerwebi.presentacion.DatosLogin"));

}
}*/
