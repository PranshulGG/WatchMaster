package com.pranshulgg.watchmaster.feature.person

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PersonViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {
    private val personDataCache = PersonDataCache

    var personData by mutableStateOf<PersonEntity?>(null)
        private set

    var loading by mutableStateOf(true)
        private set


    val fakePersonData = PersonEntity(
        name = "Brad Pitt",
        biography = "William Bradley Pitt (born December 18, 1963) is an American actor and film producer. In a film career spanning more than thirty years, Pitt has received numerous accolades, including two Academy Awards, two British Academy Film Awards, two Golden Globe Awards, two Primetime Emmy Awards and one Volpi Cup. His films as a leading actor have grossed over \$7.5 billion worldwide.\\n\\nPitt first gained recognition as a cowboy hitchhiker in the Ridley Scott road film Thelma & Louise (1991). Pitt emerged as a star taking on starring roles in films such as the drama A River Runs Through It (1992), the western Legends of the Fall (1994), the horror film Interview with the Vampire (1994), the crime thriller Seven (1995), the cult film Fight Club (1999), and the crime comedy Snatch (2000). He cemented his leading man status by starring in blockbusters such as Steven Soderbergh's Ocean's film trilogy (2001–2007), Troy (2004), Mr. & Mrs. Smith (2005), World War Z (2013), Bullet Train (2022) and F1 (2025).\\n\\nPitt earned Academy Award nominations for his performances in the science fiction drama 12 Monkeys (1995), the fantasy romance The Curious Case of Benjamin Button (2008) and the sports drama Moneyball (2011). For his portrayal of a stuntman in Quentin Tarantino's Once Upon a Time in Hollywood (2019), he won the Academy Award for Best Supporting Actor. He also starred in acclaimed films such as Babel(2006), The Assassination of Jesse James by the Coward Robert Ford (2007), Burn After Reading (2008), Inglourious Basterds (2009), The Tree of Life (2011), Fury (2014), The Big Short (2015), Ad Astra (2019) and Babylon (2022).\\n\\nIn 2001, Pitt co-founded the production company Plan B Entertainment. As a producer, he won the Academy Award for Best Picture for 12 Years a Slave (2013). He was nominated for Moneyball (2011) and The Big Short (2015). An influential figure in popular culture, Pitt appeared on Forbes' annual Celebrity 100 list from 2006 to 2008, and the Time 100 list in 2007. Regarded as a sex symbol, Pitt was named People's Sexiest Man Alive in 1995 and 2000. Pitt's relationships have also been subject to widespread media attention, particularly his marriages to actresses Jennifer Aniston and Angelina Jolie, the latter of whom he shares six children with.\\n\\nDescription above from the Wikipedia article Brad Pitt, licensed under CC-BY-SA, full list of contributors on Wikipedia.",
        birthday = "1963-12-18",
        gender = 2,
        profilePath = "/r9DzKQLNbh5QfXlrFGHoVNKER7X.jpg",
        placeOfBirth = "Shawnee, Oklahoma, USA",
        knownForDepartment = "Acting",
    )

//    init {
//        personData = fakePersonData
//        loading = false
//    }

    fun fetchPersonData(personId: Long, onError: () -> Unit) {

        if (personDataCache.get(personId) != null) {
            personData = personDataCache.get(personId)
            Log.d("PersonViewModel", "Person data found in cache")
            loading = false
            return
        }

        viewModelScope.launch {
            val person = try {
                personRepository.fetchPersonData(personId)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                onError()
                return@launch
            } finally {
                loading = false
            }

            personData = person
            personDataCache.put(personId, person)
        }

    }

}