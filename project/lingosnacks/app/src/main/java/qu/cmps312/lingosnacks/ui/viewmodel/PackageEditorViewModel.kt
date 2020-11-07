package qu.cmps312.lingosnacks.ui.viewmodel

import androidx.lifecycle.ViewModel
import qu.cmps312.lingosnacks.model.Definition
import qu.cmps312.lingosnacks.model.LearningPackage
import qu.cmps312.lingosnacks.model.Sentence
import qu.cmps312.lingosnacks.model.Word

// Used to ease pass data between Learning Package Editor Fragments
class PackageEditorViewModel : ViewModel() {
    var learningPackage = LearningPackage()
    var selectedWordIndex = 0
    fun getWordDefinitions() = learningPackage.words[selectedWordIndex].definitions
    fun setWordDefinitions(definitions: MutableList<Definition>) {
        learningPackage.words[selectedWordIndex].definitions = definitions
    }
    //var currentSentence = Sentence("")
}