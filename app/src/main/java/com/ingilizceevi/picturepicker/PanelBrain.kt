package com.ingilizceevi.picturepicker

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PanelBrain : ViewModel() {
    var numOfImages: Int = 0
    private var gameBrainIsInitialized = false
    lateinit var masterCollection: MutableList<ConceptualObject>
    private var myTargetConcept : Int = -1
    private val availableConcepts: MutableList<Int> = ArrayList(0)
    private val pileOfDiscardedConcepts:MutableList<Int> = ArrayList(0)
    private val mapOfActiveGameViews: MutableMap<Int, Int> = mutableMapOf()
    lateinit var studentInfo:StudentInfo



    private var clickedCounter = 0

    fun initiateModel(numOfLanes:Int, chapter:String){
        this.numOfImages = numOfLanes
        val concepts = ConceptLoader(chapter)
        concepts.loadAudio()
        concepts.loadDrawables()
        createConceptualObjectArray(chapter)
        availableConceptsAreGenerated()
        mapOfGameViewsIsInitialized()
        schemaIsInitializeToBeginGame()
        gameBrainIsInitialized=true
    }
    fun createConceptualObjectArray(chapter:String){
        val temp : MutableList<ConceptualObject> = ArrayList(0)
        val concept = ConceptLoader(chapter)
        concept.loadAudio()
        concept.loadDrawables()
        concept.theImages.forEach{ e->
            val str = e.key
            val d = e.value
            val s = concept.theSounds[str]!!
            val o = ConceptualObject(s,d,str)
            temp.add(o)
            }
            masterCollection = temp
    }

    fun isGameInitialized():Boolean{
        return gameBrainIsInitialized
    }

    fun getTargetUri(): Uri {
        return masterCollection[myTargetConcept].uri
    }
    private fun availableConceptsAreGenerated() {
        val size = masterCollection.size
        for(i in 0 until size){
            availableConcepts.add(i)
        }
        availableConcepts.shuffle()
    }
    private fun mapOfGameViewsIsInitialized(){
        for(i in 0 until numOfImages){
            mapOfActiveGameViews[i] = -1
        }
    }

    private fun conceptIsPulledFromAvailableConcepts():Int{
        return if(availableConcepts.isNotEmpty()) availableConcepts.removeAt(0)
        else -1
    }
    private fun conceptIsRegisteredToIdealMapFromAvailableList(laneTag:Int):Boolean{
        mapOfActiveGameViews[laneTag]=conceptIsPulledFromAvailableConcepts()
        return mapOfActiveGameViews[laneTag] != -1
    }

    private fun conceptIsThrownToDiscardPile(laneTag:Int) {
        if (mapOfActiveGameViews[laneTag] != null) {
            pileOfDiscardedConcepts.add(mapOfActiveGameViews[laneTag]!!)
            mapOfActiveGameViews[laneTag] = -1
        }
    }

    fun schemaIsInitializeToBeginGame():Boolean {
        for (i in 0 until numOfImages) {
            if(!conceptIsRegisteredToIdealMapFromAvailableList(i))return false
        }
        return true
    }

    fun getDrawableAccordingToMap(index:Int): Drawable {
        val indexToMasterCollection = mapOfActiveGameViews[index]
        return masterCollection[indexToMasterCollection!!].drawable

    }

    fun idealSchemaIsRecycled(){
        val tempList :MutableList<Int> = ArrayList(0)
        for(i in 0 until numOfImages) tempList.add(mapOfActiveGameViews[i]!!)
        tempList.shuffle()
        for(i in 0 until numOfImages) mapOfActiveGameViews[i] = tempList[i]
    }

    private fun conceptIsReturnedToAvailableList(viewId:Int){
        val concept = mapOfActiveGameViews[viewId]
        if (concept != -1) availableConcepts.add(concept!!)
        availableConcepts.shuffle()
    }

    fun allImagesAreFinished():Boolean{
        for(i in 0 until numOfImages){
            if(mapOfActiveGameViews[i]!=-1)return false
        }
        return true
    }
    fun schemaForImageClickedTrue(viewId:Int):Boolean{
        for(index in 0 until numOfImages) {
            if(index == viewId){
                conceptIsThrownToDiscardPile(index)
                conceptIsRegisteredToIdealMapFromAvailableList(index)
            }
            else{
                conceptIsReturnedToAvailableList(index)
                conceptIsRegisteredToIdealMapFromAvailableList(index)
            }
        }
        if(allImagesAreFinished())return false
        myTargetConceptIsSetFromIdealMap()
        return true
    }

    fun schemaForImageClickedFalse(){
        for(index in 0 until numOfImages){
            val tempConcept = mapOfActiveGameViews[index]
            if(tempConcept!=myTargetConcept) {
                conceptIsReturnedToAvailableList(index)
                conceptIsRegisteredToIdealMapFromAvailableList(index)
            }
        }
        idealSchemaIsRecycled()
    }
    fun targetConceptAsString():String{
        if(myTargetConcept!=null) return masterCollection[myTargetConcept!!].word
        return ""
    }

    fun getIdealLaneValue(laneTag:Int):Int?{
        return mapOfActiveGameViews[laneTag]
    }
    fun isTargetConceptTrue(laneTag: Int):Boolean{
        return mapOfActiveGameViews[laneTag]==myTargetConcept
    }

    fun getRandomConceptFromDiscardedPile():Int{
        val size = pileOfDiscardedConcepts.size
        return pileOfDiscardedConcepts[(0 until size).random()]
    }
    fun myTargetConceptIsSetFromIdealMap(){
        do{
            myTargetConcept = mapOfActiveGameViews[(0 until numOfImages).random()]!!
        } while(myTargetConcept<0)
    }

    private fun pictureIsPulledFromDiscardedPile():Int{
        if (pileOfDiscardedConcepts.isEmpty())return -1
        val size = pileOfDiscardedConcepts.size
        return pileOfDiscardedConcepts[(0..size).random()]
    }

    fun getTotalClicks():Int{return clickedCounter}
    fun increaseClickedCounter(){
        clickedCounter++
    }

    fun calculateStudentTime(totalTime:String):Int{
        val parts = totalTime.split(":")
        val minutes= parts[0].toInt()
        var seconds=parts[1].toInt()
        seconds = seconds + (minutes * 60)
        return seconds
    }

    //the following three methods initiate the live data listeners
    val startGameLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val cancelGameLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}