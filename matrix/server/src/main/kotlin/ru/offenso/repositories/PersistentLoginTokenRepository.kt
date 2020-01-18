package ru.offenso.repositories

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.stereotype.Component
import ru.offenso.entities.PersistentLogin
import java.util.*
import javax.transaction.Transactional

@Transactional
@Component
class PersistentLoginTokenRepository(
    var repo: PersistentLoginRepository,
    var userRepo: UserRepository
) : PersistentTokenRepository {
    override fun updateToken(series: String, tokenValue: String, lastUsed: Date) {
        repo.findById(series).ifPresent {
            it.token = tokenValue
            it.lastUsed = lastUsed
            repo.save(it)
        }
    }

    override fun getTokenForSeries(seriesId: String): PersistentRememberMeToken? {
        val opt = repo.findById(seriesId)
        return if (opt.isPresent) opt.get().run {
            PersistentRememberMeToken(user!!.username, series, token, lastUsed)
        } else null
    }

    override fun removeUserTokens(username: String) {
        userRepo.findByUsername(username).ifPresent {
            repo.deleteAllByUser(it)
        }
    }

    override fun createNewToken(token: PersistentRememberMeToken) {
        userRepo.findByUsername(token.username).ifPresent {
            repo.save(PersistentLogin(token.series, it, token.tokenValue, token.date))
        }
    }
}