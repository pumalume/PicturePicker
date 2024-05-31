package com.ingilizceevi.imageconcept

import android.animation.Animator
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ingilizceevi.mylibrary.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImagePanelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImagePanelFragment : Fragment() {
    private var numOfImages: Int? = null
    val imageFragmentArray: MutableList<ImageFragment> = ArrayList(0)
    lateinit var main: View
    //private val gameBrain: GameBrainModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                numOfImages = it.getInt(ARG_PARAM1)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        main = inflater.inflate(R.layout.fragment_image_panel, container, false)
        addImageViewToPanel(3)
        //if(numOfImages!=null) addImageViewToPanel(numOfImages!!)
        //else addImageViewToPanel(1)
        return main
    }

    fun handleOnImage(index:Int): ImageFragment {
        return imageFragmentArray[index]
    }
    fun addImageViewToPanel(num: Int) {
        val manager = childFragmentManager.beginTransaction()
        for (i in 0 until num) {
            imageFragmentArray.add(ImageFragment.newInstance(i))
            manager.add(R.id.linearLayoutPanel, imageFragmentArray[i], i.toString())
        }
        manager.commit()

    }

    fun panelOfFadeOutViews(): MutableList<Animator> {
        val myFadeArray: MutableList<Animator> = ArrayList(0)
        for (i in 0 until numOfImages!!) {
            myFadeArray.add(imageFragmentArray[i].fadeAnimatorIsSetForView())
        }
        return myFadeArray
    }

    fun refreshPanelOfImages() {
        for (i in 0 until numOfImages!!) {
            //imageFragmentArray[i].setImageWith()
            // imageFragmentPanel[i].loadImageFromMasterCollection()
        }
        imagePanelValuesAreReset()
    }
    fun setPanelOfImages() {
        for (i in 0 until numOfImages!!) {
            //imageFragmentArray[i].setImageWith(gameBrain.getDrawableAccordingToMap(i))
            // imageFragmentPanel[i].loadImageFromMasterCollection()
        }
        imagePanelValuesAreReset()
    }


    fun nullifyAllListeners() {
        for (i in 0 until numOfImages!!) {
            imageFragmentArray[i].handleOnImageView()
                .setOnClickListener(null)
        }
    }

    fun selectionIsTrue(viewId: Int): Animator {
        val shakeAnim = imageFragmentArray[viewId].shakerAnimatorIsSetForView()
        return shakeAnim
    }

    fun imagePanelValuesAreReset() {
        for (i in 0 until numOfImages!!) {
//                imageFragmentArray[i].imageValuesAreReset()
        }
    }

    fun selectionIsFalse(viewId: Int): Animator {
        val enlargerAnim = imageFragmentArray[viewId].enlargerAnimatorIsSetForView()
        return enlargerAnim
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            ImagePanelFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}