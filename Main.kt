package encryptdecrypt

import java.io.File

const val MIN_VALUE = 97
const val ALPHABET_SIZE = 26

fun shiftEncryption(message: String, key: Int): String {
    val encryptedMessage = StringBuilder()
    for (char in message) {
        if (char.isLetter()) {
            val shifted = if (char.isLowerCase()) {
                ((char.code + key - MIN_VALUE) % ALPHABET_SIZE + MIN_VALUE).toChar()
            } else {
                ((char.code + key - 'A'.code) % ALPHABET_SIZE + 'A'.toInt()).toChar()
            }
            encryptedMessage.append(shifted)
        } else {
            encryptedMessage.append(char)
        }
    }
    return encryptedMessage.toString()
}

fun shiftDecryption(message: String, key: Int): String {
    val decryptedMessage = StringBuilder()
    for (char in message) {
        if (char.isLetter()) {
            val shifted = if (char.isLowerCase()) {
                ((char.code - key - MIN_VALUE + ALPHABET_SIZE) % ALPHABET_SIZE + MIN_VALUE).toChar()
            } else {
                ((char.code - key - 'A'.code + ALPHABET_SIZE) % ALPHABET_SIZE + 'A'.code).toChar()
            }
            decryptedMessage.append(shifted)
        } else {
            decryptedMessage.append(char)
        }
    }
    return decryptedMessage.toString()
}

fun unicodeEncryption(message: String, key: Int): String {
    val encryptedMessage = StringBuilder()
    for (char in message) {
        encryptedMessage.append((char.code + key).toChar())
    }
    return encryptedMessage.toString()
}

fun unicodeDecryption(message: String, key: Int): String {
    val decryptedMessage = StringBuilder()
    for (char in message) {
        decryptedMessage.append((char.code - key).toChar())
    }
    return decryptedMessage.toString()
}

fun main(args: Array<String>) {
    var mode = "enc"
    var key = 0
    var inputFile = ""
    var outputFile = ""
    var algorithm = "shift"

    for (i in args.indices step 2) {
        when (args[i]) {
            "-mode" -> mode = args[i + 1]
            "-key" -> key = args[i + 1].toInt()
            "-in" -> inputFile = args[i + 1]
            "-out" -> outputFile = args[i + 1]
            "-alg" -> algorithm = args[i + 1]
        }
    }

    val readData = try {
        File(inputFile).readText()
    } catch (e: Exception) {
        println("Error: Input file not found")
        return
    }

    if (algorithm == "shift") {
        val writeResult = try {
            File(outputFile).writeText(
                when (mode) {
                    "enc" -> shiftEncryption(readData, key)
                    "dec" -> shiftDecryption(readData, key)
                    else -> ""
                }
            )
        } catch (e: Exception) {
            println("Error: Output file not found")
            return
        }

        if (writeResult.toString() == "") {
            println(
                when (mode) {
                    "enc" -> shiftEncryption(readData, key)
                    "dec" -> shiftDecryption(readData, key)
                    else -> ""
                }
            )
        }

    } else {
        val writeResult = try {
            File(outputFile).writeText(
                when (mode) {
                    "enc" -> unicodeEncryption(readData, key)
                    "dec" -> unicodeDecryption(readData, key)
                    else -> ""
                }
            )
        } catch (e: Exception) {
            println("Error: Output file not found")
            return
        }

        if (writeResult.toString() == "") {
            println(
                when (mode) {
                    "enc" -> unicodeEncryption(readData, key)
                    "dec" -> unicodeDecryption(readData, key)
                    else -> ""
                }
            )
        }
    }
}
