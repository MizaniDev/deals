package com.mizanidev.deals.util

class Url {
    companion object{
        fun baseUrl(): String {
            return "https://api-savecoins.nznweb.com.br/v1/"
        }

        fun twitterUrl(): String {
            return "https://twitter.com/rodrigomizani"
        }

        fun twitterApp(): String {
            return "twitter://user?screen_name=rodrigomizani"
        }

        fun instagramUrl(): String {
            return "https://www.instagram.com/giovannasoaresmkt"
        }

        fun instagramApp(): String {
            return "https://instagram.com/_u/giovannasoaresmkt"
        }
    }

}