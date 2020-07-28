# MVVM_simple_demo
This is the simplest but effective example of applying MVVM+LiveData+Retrofit+Room(SQLite) as shown in the diagram -> https://androidwave.com/wp-content/uploads/2019/05/mvvm-architecture-app-in-android.png
 (Except ViewModel)
 
# What does it do
When the app runs, it loads a list of posts (fetched from here -> https://jsonplaceholder.typicode.com/posts ) in a TextView. If internet is available, it fetches data from the url and saves locally. After the first load, it can fetch data later even if internet is not available.

If you are absolute beginner, first watch first 4 videos of this playlist https://www.youtube.com/watch?v=ARpn-1FPNE4&list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118 , he used only local db there. Then clone and experiment with this code.

# NB
There are 2 DAO classes (PostDao for local and RetrofitDao for remote), but a single Repository class as the MVVM model suggests. And the MainActivity communicates with only the Repository, the Repository manages local/remote data-fetches.
