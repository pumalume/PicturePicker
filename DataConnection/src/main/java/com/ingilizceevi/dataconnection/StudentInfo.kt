package com.ingilizceevi.dataconnection

class StudentInfo(id: String, fname:String, lname:String){
    var studentID:String = ""
    var studentFirstName:String = ""
    var studentLastName:String= ""
    var chapterNum:String = ""
    var chapterTime:Int = 0
    var chapterClick:Int = 0

    init{
        studentID = id
        studentFirstName = fname
        studentLastName = lname
    }
}

