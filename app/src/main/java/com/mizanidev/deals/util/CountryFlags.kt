package com.mizanidev.deals.util

class CountryFlags {
    companion object{
        private val A: String = getEmojiByUnicode(0x1F1E6)
        private val B: String = getEmojiByUnicode(0x1F1E7)
        private val C: String = getEmojiByUnicode(0x1F1E8)
        private val D: String = getEmojiByUnicode(0x1F1E9)
        private val E: String = getEmojiByUnicode(0x1F1EA)
        private val F: String = getEmojiByUnicode(0x1F1EB)
        private val G: String = getEmojiByUnicode(0x1F1EC)
        private val H: String = getEmojiByUnicode(0x1F1ED)
        private val I: String = getEmojiByUnicode(0x1F1EE)
        private val J: String = getEmojiByUnicode(0x1F1EF)
        private val K: String = getEmojiByUnicode(0x1F1F0)
        private val L: String = getEmojiByUnicode(0x1F1F1)
        private val M: String = getEmojiByUnicode(0x1F1F2)
        private val N: String = getEmojiByUnicode(0x1F1F3)
        private val O: String = getEmojiByUnicode(0x1F1F4)
        private val P: String = getEmojiByUnicode(0x1F1F5)
        private val Q: String = getEmojiByUnicode(0x1F1F6)
        private val R: String = getEmojiByUnicode(0x1F1F7)
        private val S: String = getEmojiByUnicode(0x1F1F8)
        private val T: String = getEmojiByUnicode(0x1F1F9)
        private val U: String = getEmojiByUnicode(0x1F1FA)
        private val V: String = getEmojiByUnicode(0x1F1FB)
        private val W: String = getEmojiByUnicode(0x1F1FC)
        private val X: String = getEmojiByUnicode(0x1F1FD)
        private val Y: String = getEmojiByUnicode(0x1F1FE)
        private val Z: String = getEmojiByUnicode(0x1F1FF)

        private fun getCodeByCharacter(character: Char): String{
            var code: String? = null
            when (character){
                ::A.name.toCharArray()[0] -> code = A
                ::B.name.toCharArray()[0] -> code = B
                ::C.name.toCharArray()[0] -> code = C
                ::D.name.toCharArray()[0] -> code = D
                ::E.name.toCharArray()[0] -> code = E
                ::F.name.toCharArray()[0] -> code = F
                ::G.name.toCharArray()[0] -> code = G
                ::H.name.toCharArray()[0] -> code = H
                ::I.name.toCharArray()[0] -> code = I
                ::J.name.toCharArray()[0] -> code = J
                ::K.name.toCharArray()[0] -> code = K
                ::L.name.toCharArray()[0] -> code = L
                ::M.name.toCharArray()[0] -> code = M
                ::N.name.toCharArray()[0] -> code = N
                ::O.name.toCharArray()[0] -> code = O
                ::P.name.toCharArray()[0] -> code = P
                ::Q.name.toCharArray()[0] -> code = Q
                ::R.name.toCharArray()[0] -> code = R
                ::S.name.toCharArray()[0] -> code = S
                ::T.name.toCharArray()[0] -> code = T
                ::U.name.toCharArray()[0] -> code = U
                ::V.name.toCharArray()[0] -> code = V
                ::W.name.toCharArray()[0] -> code = W
                ::X.name.toCharArray()[0] -> code = X
                ::Y.name.toCharArray()[0] -> code = Y
                ::Z.name.toCharArray()[0] -> code = Z
            }

            return code!!
        }

        private fun getEmojiByUnicode(unicode: Int) : String{
            return String(Character.toChars(unicode))
        }

        fun getCountryFlagByCountryCode(countryCode: String?): String{
            return if(countryCode?.length == 2){
                getCodeByCharacter(countryCode[0]) + getCodeByCharacter(countryCode[1])
            }else{
                ""
            }
        }
    }
}