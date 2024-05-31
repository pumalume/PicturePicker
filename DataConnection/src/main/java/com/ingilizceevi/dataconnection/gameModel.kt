package com.ingilizceevi.dataconnection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ingilizceevi.dataconnection.StudentInfo

class gameModel: ViewModel() {

    lateinit var myStudentList : MutableList<StudentInfo>
    lateinit var myChapterList : MutableList<String>
    lateinit var chosenStudent: StudentInfo
    lateinit var chosenChapter: String
    var studentIsChosen = false
    var chapterIsChosen = false
    var studentListIsInitialized = false
    var chapterListIsInitialized = false

    fun setChosenStudent(id:String){
        val student = returnStudentFromId(id)
        if(student!=null) chosenStudent = student
    }
    fun returnStudentFromId(id:String): StudentInfo?{
        myStudentList.forEach { student->
            if(student.studentID==id)return student
        }
        return null
    }

    fun concatanateStudentName():String{
        return chosenStudent.studentFirstName+" "+ chosenStudent.studentLastName
    }
    fun calculateTimeToSeconds(totalTime:String):Int{
        val parts = totalTime.split(":")
        val minutes= parts[0].toInt()
        var seconds=parts[1].toInt()
        seconds = seconds + (minutes * 60)
        chosenStudent.chapterTime = seconds
        return seconds
    }

    fun fillTempList(){
        val temp1 = StudentInfo("-1", "Mario", "Bros")
        val temp2 = StudentInfo("-2", "Luigi", "Bros")
        val temp3 = StudentInfo("-3", "Bowser Boss", "Monster")
        val temp4 = StudentInfo("-4", "Princess", "Peach")
        val temp5 = StudentInfo("-5", "Toad", "Guy")
        val mutable_list = mutableListOf<StudentInfo>(temp1,temp2,temp3, temp4, temp5)
        myStudentList = mutable_list
    }
    fun studentListIsLoadedFrom(studentList : MutableList<StudentInfo>)
    {
        myStudentList = studentList
        studentListIsInitialized = true
    }
    fun flagIsRaisedForLoadedStudentList(filledStudentList:Boolean){
            dataIsLoaded.value = filledStudentList
    }
    val dataIsLoaded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val chapterDataIsLoaded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val nameIsSelected: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val chapterIsSelected: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val quitSignal: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val startSignal: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val changeChapterSignal: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val changeNameSignal: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

}