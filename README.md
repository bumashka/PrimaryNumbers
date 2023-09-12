# Лабораторная работа №2
# Создание многопоточного приложения
## Задача
## Вычисление простых чисел 
Для решения задачи был выбран способ нахождения простых чисел в диапозоне. Каждое число диапазона проверяется на возможность деления всеми предшествующими его числами.
Основная функция нахождения простых чисел в диапазоне: возвращет набор простых чисел.

```java
public List<Integer> findPrimesInRange() {
        List<Integer> primes = new ArrayList<>();
        Arrays.stream(range).forEach(
                i -> {
                    if (i > 1) {
                        if (IntStream.range(2, i).noneMatch(n -> i % n == 0)) {
                            primes.add(i);
                        }
                    }
                }
        );
        return primes;
}
```
Для создания многопоточного приложения нахождения простых чисел был выбран метод с использованием Futures. Для этого класс PrimeNumbers наследует Callable. 

## Исследование
Ниже представлены графики взаимосвязи между количеством тредов и временем нахождения простых чисел в заданном диапазоне.

![Scatter plot for range 0 - 15000 with 25 threads](https://github.com/bumashka/PrimaryNumbers/assets/90649137/7611f6f8-d530-4586-900a-fedbdde4f170)
![Scatter plot for range 0 - 100000 with 100 threads](https://github.com/bumashka/PrimaryNumbers/assets/90649137/e11e8648-cd37-4016-8e55-818b124c7388)


Из графиков видно, что на большом диапозоне использование тредов в данной задаче оправдано, так как способно значительно увеличить скорость выполнения. При этом, начиная с определённого количества тредов график выходит на плато и всё большее количество тредов уже не улучшает времени.

На графиках ниже видно, что слишком большое количество тредов только ухудшает показатели времени и начинает прослеживаться линейная зависимость. Это связано с затратами времени на создание тредов.

![Scatter plot for range 0 - 1000 with 500 threads](https://github.com/bumashka/PrimaryNumbers/assets/90649137/febeacb2-4c20-4ed3-9a77-e83feadfd5c4)
![Scatter plot for range 0 - 1000 with 100 threads](https://github.com/bumashka/PrimaryNumbers/assets/90649137/ac7307c3-99a1-4b5d-be2f-7fd475b34020)
![Scatter plot for range 0 - 1000 with 25 threads](https://github.com/bumashka/PrimaryNumbers/assets/90649137/6ee56912-bf02-4829-80ba-a48e83965b8e)


## Выводы
Несмотря на то, что задача нахождения простых чисел довольно тривиальная, на ней очень хорошо видна зависимость времени выполнения программы от количества используемых тредов. Были сделаны выводы, что 
большое количество тредов ухудшает показатели времени выполнения данной задачи.


