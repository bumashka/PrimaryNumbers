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

[![An old rock in the desert](/assets/images/san-juan-mountains.jpg "San Juan Mountains")](https://drive.google.com/file/d/1vMDyybB5dJgoNpAlJ1qUmdPiGR7cI1vk/view?usp=sharing)
[![An old rock in the desert](/assets/images/san-juan-mountains.jpg "San Juan Mountains")](https://drive.google.com/file/d/1ve0euFQpR4ln8i5UpXpSJ7uWAB0jGy94/view?usp=sharing)

Из графиков видно, что на большом диапозоне использование тредов в данной задаче оправдано, так как способно значительно увеличить скорость выполнения. При этом, начиная с определённого количества тредов график выходит
на плато и всё большее количество тредов уже не улучшает времени.

На графиках ниже видно, что задачи со слишком большим количеством тредов только ухудшает показатели. Это связано с затратами времени на создание тредов.

[![An old rock in the desert](/assets/images/san-juan-mountains.jpg "San Juan Mountains")](https://drive.google.com/file/d/1I4FFSMf5B2cLB3PkubzNtdGFQnAC0x9v/view?usp=sharing)
[![An old rock in the desert](/assets/images/san-juan-mountains.jpg "San Juan Mountains")](https://drive.google.com/file/d/1MpkQCTKRHuq-ySbScjlIgeufcZnVzW1K/view?usp=sharing)
[![An old rock in the desert](/assets/images/san-juan-mountains.jpg "San Juan Mountains")](https://drive.google.com/file/d/1EL3I0Pe7P76ikeTpqfQCG1z7A00CfUkh/view?usp=sharing)


## Выводы
Несмотря на то, что задача нахождения простых чисел довольно тривиальная, на ней очень хорошо проявляется зависимость времени выполнения программы и количеством используемых тредов. Были сделаны выводы, что 
большое количество тредов может даже ухудшить показатели эффективности.



