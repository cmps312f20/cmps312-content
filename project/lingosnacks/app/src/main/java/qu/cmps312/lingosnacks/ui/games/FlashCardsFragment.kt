package qu.cmps312.lingosnacks.ui.games

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Sentence
import kotlinx.android.synthetic.main.fragment_flash_cards.*
import qu.cmps312.lingosnacks.model.Resource
import qu.cmps312.lingosnacks.model.ResourceTypeEnum
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel

class WordInfo(val word:String, val sentence: Sentence, val type: String = "S")

class FlashCardsFragment : Fragment(R.layout.fragment_flash_cards) {
    private val packageViewModel by activityViewModels<PackageViewModel>()
    private var currentIndex = 0
    private lateinit var sentences: List<WordInfo>
    private val previousButtons = mutableListOf<ImageButton>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedPackage = packageViewModel.selectedPackage!!

        // Merge all into 1 list of sentences
        sentences =
            selectedPackage.words.flatMap { word ->
                word.sentences.map { WordInfo(word.text, it) }
            } + selectedPackage.words.flatMap { word ->
                word.definitions.map { WordInfo(word.text, Sentence(it.text), "D") }
            }

        displaySentence(currentIndex)
        nextBtn.setOnClickListener { onMoveNext(it) }
        prevBtn.setOnClickListener { onMoveNext(it) }
    }

    private fun onMoveNext(view: View) {
        imageView.isVisible = false
        videoView.isVisible = false
        webView.isVisible = false

        when (view.id) {
            R.id.nextBtn -> ++currentIndex
            R.id.prevBtn -> --currentIndex
            else -> return
        }
        //If current index becomes == count then it will be assigned 0
        currentIndex = (currentIndex + sentences.size) % sentences.size
        displaySentence(currentIndex)
    }

    private fun displaySentence(index: Int) {
        currentIndexTv.text = "${index + 1} of ${sentences.size}"
        definitionEt.text = sentences[index].word
        val sentence =
            if (sentences[index].type == "D") "Definition: ${sentences[index].sentence.text}" else sentences[index].sentence.text
        sentenceTv.text = sentence

        previousButtons.forEach {
            mainLayout.removeView(it)
        }
        previousButtons.clear()
        sentences[index].sentence.resources.forEachIndexed { index, resource ->
            println(">> Debug: ${resource.title}")
            val imgBtn = createButton(resource)
            imgBtn.setOnClickListener {
                Toast.makeText(requireContext(), "${resource.title}", Toast.LENGTH_SHORT).show()
                when (resource.type) {
                    ResourceTypeEnum.Photo -> displayPhoto(resource.resourceUrl)
                    ResourceTypeEnum.Video -> playVideo(resource.resourceUrl)
                    ResourceTypeEnum.Website -> viewPage(resource.resourceUrl)
                }
            }
            mainLayout.addView(imgBtn)
            mediaFlow.addView(imgBtn)
            if (index == 0) imgBtn.performClick()
            previousButtons.add(imgBtn)
        }
    }

    private fun displayPhoto(resourceUrl: String) {
        webView.isVisible = false
        videoView.isVisible = false
        imageView.isVisible = true
        imageView.load(resourceUrl)
    }

    private fun playVideo(resourceUrl: String) {
        webView.isVisible = false
        videoView.isVisible = true
        imageView.isVisible = false

        try {
            val mediaController = MediaController(requireContext())
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setVideoPath(resourceUrl)
            //videoView.setVideoURI(videoUri)
            videoView.requestFocus()
            videoView.start()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading video ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun viewPage(resourceUrl: String) {
        webView.isVisible = true
        videoView.isVisible = false
        imageView.isVisible = false
        try {
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    url: String
                ): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webView.loadUrl(resourceUrl)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading page ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun createButton(resource: Resource): ImageButton {
        val imageBtn = ImageButton(requireContext())

        val iconId = when (resource.type) {
            ResourceTypeEnum.Photo -> R.drawable.ic_photo
            ResourceTypeEnum.Video -> R.drawable.ic_video
            ResourceTypeEnum.Website -> R.drawable.ic_link
        }
        val photoIcon = ContextCompat.getDrawable(requireContext(), iconId)
        imageBtn.setImageDrawable(photoIcon)
        imageBtn.setPadding(25, 25, 25, 25)
        imageBtn.id = View.generateViewId()
        return imageBtn
    }
}
