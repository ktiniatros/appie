# MVVM + AndroidX + Dagger2 + Testing

This is an assignment that demonstrates how to structure a sample Android App written in Kotlin.
It consists of two screens with data fetched from [the Launch Library](https://launchlibrary.net/docs/1.4/api.html#launch) which provides an API for a repository of rocket launch information that application developers can use.

The first screen displays the next ten launches in a RecyclerView, which can be refreshed with the well known pull to refresh UX pattern. Every row displays the launch name (aligned left) and the missions names involved in this launch (aligned right). The user can filter on the missions names typing in the search view lying in the actionbar.

The second screen displays the name of the launch  in the actionbar, a map with a marker on the location the launch is taking place and the time remaining until it happens.

Dagger is used to inject our own classes in the android components so we can have more control and test easier with mocks where applicable. Every logic that does not involve Android APIs is separated to plain Kotlin classes so they can be easily unit tested. Espresso is used to test the UI via instrumented tests. At least all the basic features should be tested, eg the user can filter the launches or be able to trigger the next screen. The tests should also be independent of external, out of our control factors, like providing data from an external API, so they can be consistent and not flaky.    