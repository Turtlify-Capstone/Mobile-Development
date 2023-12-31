package com.bangkit.turtlify.utils


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.turtlify.data.repository.Repository
import com.bangkit.turtlify.ui.encyclopedia.EncyclopediaViewModel
import com.bangkit.turtlify.ui.faq.FaqViewModel
import com.bangkit.turtlify.ui.history.HistoryViewModel
import com.bangkit.turtlify.ui.identifier.IdentifierViewModel
import com.bangkit.turtlify.ui.homescreen.HomeViewModel
import com.bangkit.turtlify.ui.search.SearchViewModel
import com.bangkit.turtlify.ui.maps.MapsViewModel
import com.bangkit.turtlify.ui.suggestion.SuggestionViewModel
import com.bangkit.turtlify.ui.viemodels.ReportTurtleViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FaqViewModel::class.java) -> {
                FaqViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SuggestionViewModel::class.java) -> {
                SuggestionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ReportTurtleViewModel::class.java) -> {
                ReportTurtleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(IdentifierViewModel::class.java) -> {
                IdentifierViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EncyclopediaViewModel::class.java) -> {
                EncyclopediaViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}