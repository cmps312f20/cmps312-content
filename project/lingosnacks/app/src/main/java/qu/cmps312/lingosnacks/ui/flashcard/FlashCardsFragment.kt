package qu.cmps312.lingosnacks.ui.flashcard

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
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
    private lateinit var sentences : List<WordInfo>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedPackage = packageViewModel.selectedPackage!!

        // Merge all into 1 list of sentences
        sentences =
            selectedPackage.words.flatMap {word ->
                 word.sentences.map { WordInfo(word.text, it) }
            } + selectedPackage.words.flatMap {word ->
                 word.definitions.map { WordInfo(word.text, Sentence(it.text), "D") }
            }

        displaySentence(currentIndex)
        nextBtn.setOnClickListener { onButtonClicked( it ) }
        prevBtn.setOnClickListener { onButtonClicked( it ) }
    }

    private fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.nextBtn -> ++currentIndex
            R.id.prevBtn -> --currentIndex
            else -> return
        }
        //If current index becomes == count then it will be assigned 0
        currentIndex = (currentIndex + sentences.size) % sentences.size
        displaySentence(currentIndex)
    }

    private fun displaySentence(index : Int) {
        wordTv.text = sentences[index].word
        sentenceTv.text = sentences[index].sentence.text

        //mediaFlow.removeAllViews()
        sentences[index].sentence.resources.forEach { resource ->
            val imgBtn = createButton(resource)
            imgBtn.setOnClickListener {
                //Toast.makeText(requireContext(), "url = ${resource.resourceUrl}", Toast.LENGTH_SHORT).show()
                when (resource.type) {
                    ResourceTypeEnum.Photo -> {
                        webView.isVisible = false
                        videoView.isVisible = false
                        imageView.isVisible = true
                        imageView.load(resource.resourceUrl)
                    }
                    ResourceTypeEnum.Video -> {
                        webView.isVisible = false
                        videoView.isVisible = true
                        imageView.isVisible = false
                        videoView.apply {
                            setVideoPath(resource.resourceUrl)
                            start()
                        }
                    }
                    ResourceTypeEnum.Website -> {
                        webView.isVisible = true
                        videoView.isVisible = false
                        imageView.isVisible = false
                        webView.webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                url: String
                            ): Boolean {
                                view?.loadUrl(url)
                                return true
                            }
                        }
                        webView.loadUrl(resource.resourceUrl)
                    }
                }
            }
            mainLayout.addView(imgBtn)
            mediaFlow.addView(imgBtn)
        }
    }

    private fun createButton(resource: Resource) : ImageButton {
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
        /*imageButton.apply {
            this.text = text
            this.tag = tag
            textSize = 24F
            typeface = Typeface.DEFAULT_BOLD
            setPadding(25, 25, 25, 25)
            if (tag.isNotEmpty())
                setBackgroundResource(android.R.color.holo_green_light)
            else
                setBackgroundResource(android.R.color.holo_blue_light)
            id = View.generateViewId()
        }*/
    }
/*
    fun onWordClicked(word: Word) {
        wordTitleTv.text = word.text.capitalize()

        var position = 0
        var positionDefinition = 0
        var positionSentence = 0
        var positionResource = 0
        var sentenceResourceCount: Int

        selectFlashcardAlertTv.isVisible = false
        prevButton.isVisible = false
        nextButton.isVisible = true

        packageViewModel.selectedWord(word)

        definitionList = packageViewModel.wordDefinitions
        sentenceList = packageViewModel.wordSentences

        include.isVisible = true

        displayDefintionOnCard(definitionList[positionDefinition])

        nextButton.setOnClickListener {
            prevButton.isVisible = true
            position++

            if (position < definitionList.size) {
                positionDefinition++
                displayDefintionOnCard(definitionList[positionDefinition])
            } else {
                if (position == definitionList.size) positionDefinition++

                if (positionSentence < sentenceList.size) {
                    sentenceResourceCount = sentenceList[positionSentence].resources.size
                    if (positionResource < sentenceResourceCount - 1) {
                        displayItemWithResource(sentenceList[positionSentence], positionResource)
                        positionResource++
                    } else if (positionResource == sentenceResourceCount - 1) {
                        displayItemWithResource(sentenceList[positionSentence], positionResource)
                        if (positionSentence == sentenceList.size - 1)
                            nextButton.isVisible = false
                        else {
                            positionSentence++
                            positionResource = 0
                        }
                    }
                }


            }
        }


        prevButton.setOnClickListener {
            position--

            nextButton.isVisible = true

            if (position >= definitionList.size) {
                sentenceResourceCount = sentenceList[positionSentence].resources.size

                if (positionResource > 0) {
                    positionResource--
                    displayItemWithResource(sentenceList[positionSentence], positionResource)
                } else if (positionResource == 0) {
                    if (positionSentence == 0) {
                        displayDefintionOnCard(definitionList[positionDefinition])

                    } else {
                        positionSentence--
                        sentenceResourceCount = sentenceList[positionSentence].resources.size
                        positionResource = sentenceResourceCount - 1
                        displayItemWithResource(sentenceList[positionSentence], positionResource)
                    }
                }
            } else {
                positionSentence = 0
                positionResource = 0

                positionDefinition--
                displayDefintionOnCard(definitionList[positionDefinition])
                if (positionDefinition == 0) prevButton.isVisible = false

            }
        }

    }

    fun displayDefintionOnCard(definition: Definition) {
        include.apply {
            sentenceExampleTitleTv.isVisible = false
            imageTitleTv.isVisible = false
            videoTitleTv.isVisible = false
            websiteTitleTv.isVisible = false
            viewWebsiteButton.isVisible = false
            viewImageButton.isVisible = false
            viewVideoButton.isVisible = false

            definitionTitleTv.isVisible = true
            sourceTitleTv.isVisible = true
            sourceTv.isVisible = true

            definSentenceTv.text = definition.text
            sourceTv.text = definition.source
        }

    }

    fun displayItemWithResource(sentence: Sentence, resourceNum: Int) {
        include.apply {
            sentenceExampleTitleTv.isVisible = true

            definitionTitleTv.isVisible = false
            sourceTitleTv.isVisible = false
            sourceTv.isVisible = false


            definSentenceTv.text = sentence.text

            var resources = sentence.resources

            when (resources[resourceNum].type) {
                ResourceTypeEnum.Photo -> {
                    videoTitleTv.isVisible = false
                    viewVideoButton.isVisible = false
                    sourceTv.isVisible = false
                    websiteTitleTv.isVisible = false
                    viewWebsiteButton.isVisible = false

                    imageTitleTv.isVisible = true
                    viewImageButton.isVisible = true

                    viewImageButton.setOnClickListener {
                        packageViewModel.setResource( resources[resourceNum])
                        findNavController().navigate(R.id.toDisplayMediaDialog)
                    }
                }

                ResourceTypeEnum.Video -> {
                    imageTitleTv.isVisible = false
                    sourceTv.isVisible = false
                    websiteTitleTv.isVisible = false
                    viewImageButton.isVisible = false
                    viewWebsiteButton.isVisible = false

                    videoTitleTv.isVisible = true
                    viewVideoButton.isVisible = true

                    viewVideoButton.setOnClickListener {
                        packageViewModel.setResource(resources[resourceNum])
                        findNavController().navigate(R.id.toDisplayMediaDialog)
                    }
                }

                ResourceTypeEnum.Website -> {
                    websiteTitleTv.isVisible = true
                    viewWebsiteButton.isVisible = true

                    imageTitleTv.isVisible = false
                    viewImageButton.isVisible = false
                    videoTitleTv.isVisible = false
                    viewVideoButton.isVisible = false
                    sourceTv.isVisible = false

                    viewWebsiteButton.setOnClickListener {
                        packageViewModel.setResource(resources[resourceNum])
                        findNavController().navigate(R.id.to_webPageDialog)
                    }
                }
            }

        }
    }*/
}
