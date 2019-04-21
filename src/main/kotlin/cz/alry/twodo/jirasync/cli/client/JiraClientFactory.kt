package cz.alry.twodo.jirasync.cli.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import feign.Feign
import feign.auth.BasicAuthRequestInterceptor
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.slf4j.Slf4jLogger
import org.slf4j.LoggerFactory

/**
 * @author Ales Rybak(ales.rybak@lundegaard.eu)
 */
class JiraClientFactory {

    private val mapper = ObjectMapper().registerModule(KotlinModule())
    private val jacksonEncoder = JacksonEncoder(mapper)
    private val jacksonDecoder = JacksonDecoder(mapper)
    private val slf4jLogger = Slf4jLogger()


    fun getInstance(jiraHost: String, jiraLogin: String, jiraPassword: String): JiraClient = Feign
            .builder()
            .requestInterceptor(BasicAuthRequestInterceptor(jiraLogin, jiraPassword))
            .encoder(jacksonEncoder)
            .decoder(jacksonDecoder)
            .logger(slf4jLogger)
            .logLevel(feign.Logger.Level.FULL)
            .target(JiraClient::class.java, jiraHost)


    companion object {
        @JvmStatic
        private val LOG = LoggerFactory.getLogger(JiraClientFactory::class.java)
    }
}
