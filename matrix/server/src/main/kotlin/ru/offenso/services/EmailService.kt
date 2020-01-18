package ru.offenso.services

import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.ApplicationContext
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import ru.offenso.properties.AppProperties
import java.io.ByteArrayInputStream

@Service
class EmailService(
    private val context: ApplicationContext,
    private val props: AppProperties
) {
    fun emailSender(block: JavaMailSender.() -> Unit) {
        try {
            context.getBean(JavaMailSender::class.java)
        } catch (e: NoSuchBeanDefinitionException) {
            null
        }?.block()
    }

    fun send(
        to: String,
        cc: String = "",
        template: MailTemplate,
        values: Map<String, String> = mapOf(),
        attachments: Map<String, ByteArray> = mapOf()
    ) = emailSender {
        send(createMimeMessage().also { msg ->
            msg.setFrom(props.email.from)
            MimeMessageHelper(msg, true).run {
                setTo(to)
                if (cc.isNotEmpty()) setCc(cc)
                setSubject(template.subject.replace(Regex("\\{([^}]+)}")) {
                    values[it.groups[1]?.value.orEmpty()].orEmpty()
                })
                setText(template.message.replace(Regex("\\{([^}]+)}")) {
                    values[it.groups[1]?.value.orEmpty()].orEmpty()
                })
                attachments.forEach {
                    addAttachment(it.key) {
                        ByteArrayInputStream(it.value)
                    }
                }
            }
        })
    }

    enum class MailTemplate(val subject: String, val message: String) {
        REPORT("Новый отчет {Метаполе}: {Объект}", "Метаполе: {title}\n{values}"),
        FORGOT("Forgot password", "{link}")
    }
}