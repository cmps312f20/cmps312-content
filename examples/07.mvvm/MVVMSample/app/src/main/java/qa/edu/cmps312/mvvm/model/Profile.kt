package qa.edu.cmps312.mvvm.model

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import qa.edu.cmps312.mvvm.BR
import kotlin.properties.Delegates

enum class Seniority {
    FRESHMAN,
    JUNIOR,
    SENIOR
}

private const val quLogo = "https://www.qu.edu.qa/static_file/qu/About/images/logotype.png"
private const val spongeBob = "https://pngimg.com/uploads/spongebob/spongebob_PNG61.png"
data class Profile(private var _firstName: String,
                   private var _lastName: String,
                   var _yearsExperience: Int = 0,
                   var _photoUrl: String = spongeBob) : BaseObservable() {
    @get:Bindable
    var firstName
        get() = _firstName
        set(value) {
            _firstName = value
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var lastName
        get() = _lastName
        set(value) {
            _lastName = value
            notifyPropertyChanged(BR.lastName)
        }

    @get:Bindable
    var yearsExperience
        get() = _yearsExperience
        set(value) {
            _yearsExperience = value
            notifyPropertyChanged(BR.yearsExperience)
            notifyPropertyChanged(BR.seniority)
        }

    @get:Bindable
    val seniority: Seniority
        get() =
            when {
                yearsExperience > 9 -> Seniority.SENIOR
                yearsExperience > 4 -> Seniority.JUNIOR
                else -> Seniority.FRESHMAN
            }

    @get:Bindable
    var photoUrl
        get() = _photoUrl
        set(value) {
            _photoUrl = value
            notifyPropertyChanged(BR.photoUrl)
        }

    fun onIncYearsExperience() {
        yearsExperience++
    }
}

/*
// Another way of doing it
@get:Bindable
var firstName
     get() = _firstName
     set(value) {
         _firstName = value
         notifyPropertyChanged(BR.firstName)
     }

@get:Bindable
var lastName
    get() = _lastName
    set(value) {
        _lastName = value
        notifyPropertyChanged(BR.lastName)
    }

@get:Bindable
var yearsExperience
    get() = _yearsExperience
    set(value) {
        _yearsExperience = value
        notifyPropertyChanged(BR.yearsExperience)
        notifyPropertyChanged(BR.seniority)
    }

@get:Bindable
val seniority: Seniority
    get() =
        when {
            yearsExperience > 9 -> Seniority.SENIOR
            yearsExperience > 4 -> Seniority.JUNIOR
            else -> Seniority.FRESHMAN
        }

@get:Bindable
var photoUrl
    get() = _photoUrl
    set(value) {
        _photoUrl = value
        notifyPropertyChanged(BR.photoUrl)
    }
*/

// https://kotlinlang.org/docs/reference/delegated-properties.html#observable
/*
Delegates.observable handler is called every time we assign a value to the property.
It has three parameters: a property being assigned to, the old value and the new one
*/
/*
@get:Bindable
var firstName by Delegates.observable(_firstName) { prop, old, new ->
    Log.d("firstName", new)
    notifyPropertyChanged(BR.firstName)
}

@get:Bindable
var lastName by Delegates.observable(_lastName) { prop, old, new ->
    notifyPropertyChanged(BR.lastName)
}

@get:Bindable
var yearsExperience by Delegates.observable(_yearsExperience) { prop, old, new ->
    Log.d("yearsExperience", new.toString())
    notifyPropertyChanged(BR.yearsExperience)
    notifyPropertyChanged(BR.seniority)
}

@get:Bindable
val seniority: Seniority
    get() =
        when {
            yearsExperience > 9 -> Seniority.SENIOR
            yearsExperience > 4 -> Seniority.JUNIOR
            else -> Seniority.FRESHMAN
        }

@get:Bindable
var photoUrl by Delegates.observable(_photoUrl) { prop, old, new ->
    notifyPropertyChanged(BR.photoUrl)
}
*/