package nl.giorgos.launchme.launch.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import nl.giorgos.launchme.launch.list.LaunchListViewModel

class ViewModelsRepository() {
    fun getLaunchListViewModel(activity: AppCompatActivity): LaunchListViewModel {
        return ViewModelProviders.of(activity).get(LaunchListViewModel::class.java)
    }

    fun <T: AndroidViewModel> getViewModel(activity: AppCompatActivity, viewModelClass: Class<T>): T {
        return ViewModelProviders.of(activity).get(viewModelClass)
    }
}