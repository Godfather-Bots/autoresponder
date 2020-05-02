# Abstract autoresponder
Абстрактная реализация, которая позволяет создавать ботов. Боты позволяют автоматизировать взаимодействие с пользователем.
Боты позволяют автоматизировать общение с клиентами, отвечая по запрограммированию сценарию.

### Основные понятия

**Unit** - некая единица сценария, по которому проходит пользователь, отправляя свои сообщения. 
Каждый юнит хранит в себе ключевые слова или регулярное выражение, ссылки на следующие Unit-ы, а так же значение 
приоритета и процент количества найденных ключевых слов к заданным ключевым словам. В данном модуле Unit является 
абстрактным классом, так как юнит по задумке это расширяемый класс, который может не только отдавать текстовые 
сообщения, но и, например, сохранять пользовательскую информацию, и выполнять прочие функции. Примеры наследников
Unit можно найти в [этом репозитории](https://github.com/uPagge/social-bot).

**Сценарий** - это связанные между собой юниты. 

### Принцип работы

Для понимания рассмотрим небольшой пример по схеме изображенной ниже. Так же небольшие примеры обработки есть в тестах.

![Картинка](https://raw.githubusercontent.com/uPagge/images/master/img/autoresponder/units.jpg)

Сценариями в данном случае являются:

• Unit1 —> Unit2 —> Unit5 —> Unit7 —> Unit9  
• Unit1 —> Unit2 —> Unit6 —> Unit8;  
• Unit1 —> Unit3 —> Unit6 —> Unit8;  
• Unit1 —> Unit4 —> Unit9;  
• Unit10.

Пользователь присылает боту свое первое сообщение, например, «Привет». Если сообщение удовлетворяет регулярному 
выражению или содержит необходимое количество ключевых слов Unit1, то возвращается Unit1, то же самое относится к Unit10.

Если оба юнита удовлетворяют запросу пользователя, то будет возвращен юнит с большим приоритетом (поле priority). Если 
приоритеты равны, то случайный Unit. В нашем примере, на первое сообщение, пользователь получил Unit1, поэтому следующее 
сообщение пользователя будет ассоциироваться с множеством: Unit2, Unit3 и Unit4.

### Програмная реализация

Данная библиотека предназначена только на определение того, какой Unit отдавать, необходимо создать наследника 
класса `Unit` (самый простой вариант лежит в тестах). Следом создается объект класса `UnitPointerService`. Он отвечает
за сохранение позиции пользователя в сценарии, простыми словами он сохраняет последний Unit, который был отправлен 
пользователю. 

Далее создается объект класса `AutoResponder`. В конструктор передается `UnitPointerService` и множество юнитов, которые
будут проверяется при первом сообщении пользователя.

Далее у объекта `AutoResponder` вызывается метод `answer`, который возвращает следующий для пользователя Unit.

### Программные особенности

1. Существует возможность задать юнит, который будет возвращаться при отсутствии юнита, удвлетворяющего сообщению 
пользователя (без ключевых слов, и т.п.). Для этого у объекта `AutoResponder` вызывается метод `setDefaultUnit`.
2. Последний юнит сценария не сохраняется в `UnitPointerService`, вместо этого происходит удаление данных о позиции 
пользователя в сценарии, и следующие сообщение пользователя будет сново асоциироваться с начальным множеством юнитов.
