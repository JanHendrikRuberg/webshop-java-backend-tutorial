package de.oncoding.webshop.schedulerjobs

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SendAnalyticsJob {

    @Scheduled(cron = "*/5 * * * * ?")
    fun sendAnalytics() {
        println("Analytics are being sent")
    }
}