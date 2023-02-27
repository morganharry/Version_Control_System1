package svcs

import java.io.File

fun help() {
    println(
        "These are SVCS commands:\n" +
                "config     Get and set a username.\n" +
                "add        Add a file to the index.\n" +
                "log        Show commit logs.\n" +
                "commit     Save changes.\n" +
                "checkout   Restore a file."
    )
}

fun config(args: Array<String>) {
    val fileConfig = File("vcs/config.txt")
    if (args.size < 2) {
        val text = fileConfig.readText()
        if (text.length == 0) println("Please, tell me who you are.")
        else println("The username is ${fileConfig.readText()}.")
    } else {
        val name = args[1]
        fileConfig.writeText(args[1])
        println("The username is ${fileConfig.readText()}.")
    }
}

fun add(args: Array<String>) {
    val fileIndex = File("vcs/index.txt")
    if (args.size < 2) {
        val text = fileIndex.readLines()
        if (text.size == 0) println("Add a file to the index.")
        else {
            println("Tracked files:")
            println(text.joinToString("\n"))
        }
    } else {
        val name = args[1]
        val fileAdd = File("${name}")
        if (!fileAdd.exists()) println("Can't find '${name}'.")
        else {
            fileIndex.appendText("${name}\n")
            println("The file '${name}' is tracked.")
        }
    }
}

fun log(args: Array<String>) {
    val fileLog = File("vcs/log.txt")
    val text = fileLog.readLines()
    if (text.size == 0) println("No commits yet.")
    else {
        println(text.joinToString("\n"))
    }
}

fun commit(args: Array<String>) {
    if (args.size < 2) {
        println("Message was not passed.")
    } else {
        val hash2 = hashCheck()
        val fileHash = File("vcs/commits/${hash2}")
        if (fileHash.exists()) println("Nothing to commit.")
        else {
            val textPrev = args.toMutableList()
            textPrev.removeAt(0)
            val text1 = textPrev.joinToString(" ")
            var text = ""
            for (i in text1.indices) {
                if (text1[i] != '\"')
                    text = text + text1[i]
            }
            val fileLog = File("vcs/log.txt")
            val fileConfig = File("vcs/config.txt")
            val textLog = fileLog.readLines().joinToString("\n")
            fileLog.writeText(
                "commit ${hash2}\n" +
                        "Author: ${fileConfig.readText()}\n" +
                        "${text}\n"
            )
            fileLog.appendText(textLog)
            val fileIndex = File("vcs/index.txt")
            val files = fileIndex.readLines()
            files.forEach {
                val fileName = File(it)
                val fileCommits = File("vcs/commits/${hash2}/${it}")
                fileName.copyTo(fileCommits)
            }

            println("Changes are committed.")

        }
    }
}

fun checkout(args: Array<String>) {
    if (args.size < 2) {
        println("Commit id was not passed.")
    } else {
        val idCommit = args[1]
        val fileCommit = File("vcs/commits/${idCommit}")
        if (!fileCommit.exists()) println("Commit does not exist.")
        else {
            val fileIn = fileCommit
            //println(fileIn.listFiles().joinToString (" "))
            val fileOut = File(".")
            //println(fileOut.listFiles().joinToString (" "))
            fileIn.copyRecursively(fileOut, overwrite = true)
            println("Switched to commit ${idCommit}.")
        }
    }
}

fun wrong(comand: String) {
    println("'$comand' is not a SVCS command.")
}

fun makeFile() {
    val dirVcs = File("vcs")
    dirVcs.mkdir()
    val dirCommits = File("vcs/commits")
    dirCommits.mkdir()
    val fileConfig = File("vcs/config.txt")
    fileConfig.createNewFile()
    val fileIndex = File("vcs/index.txt")
    fileIndex.createNewFile()
    val fileLog = File("vcs/log.txt")
    fileLog.createNewFile()
}

fun hashCheck(): Int {
    val hashList = mutableListOf<Int>(0)
    val fileIndex = File("vcs/index.txt")
    val files = fileIndex.readLines()
    files.forEach {
        val fileName = File(it)
        val text = fileName.readLines()
        val hash = text.joinToString("").hashCode()
        hashList.add(hash)
    }
    var hash = 0
    hashList.forEach() {
        hash += it
    }
    return hash
}

fun main(args: Array<String>) {
    makeFile()
    if (!args.isEmpty()) {
        var comand = args.first()
        when (comand) {
            "--help" -> help()
            "config" -> config(args)
            "add" -> add(args)
            "log" -> log(args)
            "commit" -> commit(args)
            "checkout" -> checkout(args)
            else -> wrong(comand)
        }
    } else help()
}