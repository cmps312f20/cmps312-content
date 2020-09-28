package qa.edu.cmps312.mvvm.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import qa.edu.cmps312.mvvm.BR

enum class Seniority {
    FRESHMAN,
    JUNIOR,
    SENIOR
}

data class Profile(private var _firstName: String,
                   private var _lastName: String,
                   var _yearsExperience: Int = 0) : BaseObservable() {

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

    fun onIncYearsExperience() {
        yearsExperience++
    }
}

/*
data class Profile(val firstName: String, val lastName: String, var likes: ObservableInt) {
    val popularity: Popularity
        get() =
            when {
                likes.get() > 9 -> Popularity.STAR
                likes.get() > 4 -> Popularity.POPULAR
                else -> Popularity.NORMAL
            }

    fun onLike() {
        likes.set(likes.get() + 1)
    }
}

 */