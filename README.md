# Программирование. Desktop-приложение. Семестровый проект.
:disappointed: Генератор вариантов на проекты изменился, поэтому описанное задание может отличаться от реализованного кода.

### 1 этап
<p>Реализовать консольное приложение, которое реализует управление коллекцией объектов в интерактивном режиме. В коллекции необходимо хранить объекты класса <code>Movie</code>, описание которого приведено ниже.</p>
<p><b>Разработанная программа должна удовлетворять следующим требованиям:</b></p>
<ul><li>Класс, коллекцией экземпляров которого управляет программа, должен реализовывать сортировку по умолчанию.</li><li>Все требования к полям класса (указанные в виде комментариев) должны быть выполнены.</li><li>Для хранения необходимо использовать коллекцию типа <code>java.util.ArrayDequeue</code></li><li>При запуске приложения коллекция должна автоматически заполняться значениями из файла.</li><li>Имя файла должно передаваться программе с помощью: <b>переменная окружения</b>.</li><li>Данные должны храниться в файле в формате <code>json</code></li><li>Чтение данных из файла необходимо реализовать с помощью класса <code>java.io.BufferedReader</code></li><li>Запись данных в файл необходимо реализовать с помощью класса <code>java.io.PrintWriter</code></li><li>Все классы в программе должны быть задокументированы в формате javadoc.</li><li>Программа должна корректно работать с неправильными данными (ошибки пользовательского ввода, отсутсвие прав доступа к файлу и т.п.).</li></ul>
<p><b>В интерактивном режиме программа должна поддерживать выполнение следующих команд:</b></p>
<ul><li><code>help</code> : вывести справку по доступным командам</li><li><code>info</code> : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)</li><li><code>show</code> : вывести в стандартный поток вывода все элементы коллекции в строковом представлении</li><li><code>add {element}</code> : добавить новый элемент в коллекцию</li><li><code>update id {element}</code> : обновить значение элемента коллекции, id которого равен заданному</li><li><code>remove_by_id id</code> : удалить элемент из коллекции по его id</li><li><code>clear</code> : очистить коллекцию</li><li><code>save</code> : сохранить коллекцию в файл</li><li><code>execute_script file_name</code> : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.</li><li><code>exit</code> : завершить программу (без сохранения в файл)</li><li><code>remove_greater {element}</code> : удалить из коллекции все элементы, превышающие заданный</li><li><code>remove_lower {element}</code> : удалить из коллекции все элементы, меньшие, чем заданный</li><li><code>history</code> : вывести последние 14 команд (без их аргументов)</li><li><code>count_less_than_mpaa_rating mpaaRating</code> : вывести количество элементов, значение поля mpaaRating которых меньше заданного</li><li><code>filter_by_oscars_count oscarsCount</code> : вывести элементы, значение поля oscarsCount которых равно заданному</li><li><code>filter_less_than_genre genre</code> : вывести элементы, значение поля genre которых меньше заданного</li></ul>
<p><b>Формат ввода команд:</b></p>
<ul><li>Все аргументы команды, являющиеся стандартными типами данных (примитивные типы, классы-оболочки, String, классы для хранения дат), должны вводиться в той же строке, что и имя команды.</li><li>Все составные типы данных (объекты классов, хранящиеся в коллекции) должны вводиться по одному полю в строку.</li><li>При вводе составных типов данных пользователю должно показываться приглашение к вводу, содержащее имя поля (например, "Введите дату рождения:")</li><li>Если поле является enum'ом, то вводится имя одной из его констант (при этом список констант должен быть предварительно выведен).</li><li>При некорректном пользовательском вводе (введена строка, не являющаяся именем константы в enum'е; введена строка вместо числа; введённое число не входит в указанные границы и т.п.) должно быть показано сообщение об ошибке и предложено повторить ввод поля.</li><li>Для ввода значений null использовать пустую строку.</li><li>Поля с комментарием "Значение этого поля должно генерироваться автоматически" не должны вводиться пользователем вручную при добавлении.</li></ul>
<p><b>Описание хранимых в коллекции классов: </b></p>



```java
public class Movie {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private long length; //Значение поля должно быть больше 0
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person operator; //Поле не может быть null
}
public class Coordinates {
    private long x; //Значение поля должно быть больше -223
    private Long y; //Поле не может быть null
}
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long weight; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null
}
public enum MovieGenre {
    ACTION,
    MUSICAL,
    ADVENTURE,
    THRILLER;
}
public enum MpaaRating {
    PG,
    PG_13,
    R,
    NC_17;
}
public enum Color {
    GREEN,
    RED,
    BLACK,
    BROWN;
}
public enum Color {
    BLUE,
    WHITE,
    BROWN;
}
public enum Country {
    CHINA,
    ITALY,
    THAILAND;
}
```
### 2 этап
<p>Разделить программу из лабораторной работы №5</a> на клиентский и серверный модули. Серверный модуль должен осуществлять выполнение команд по управлению коллекцией. Клиентский модуль должен в интерактивном режиме считывать команды, передавать их для выполнения на сервер и выводить результаты выполнения.</p>
<p><b>Необходимо выполнить следующие требования:</b></p>
<ul><li>Операции обработки объектов коллекции должны быть реализованы с помощью Stream API с использованием лямбда-выражений.</li><li>Объекты между клиентом и сервером должны передаваться в сериализованном виде.</li><li>Объекты в коллекции, передаваемой клиенту, должны быть отсортированы по размеру</li><li>Клиент должен корректно обрабатывать временную недоступность сервера.</li><li>Обмен данными между клиентом и сервером должен осуществляться по протоколу TCP</li><li>Для обмена данными на сервере необходимо использовать <b>сетевой канал</b></li><li>Для обмена данными на клиенте необходимо использовать <b>потоки ввода-вывода</b></li><li>Сетевые каналы должны использоваться в неблокирующем режиме.</li></ul>
<p><b>Обязанности серверного приложения:</b></p>
<ul><li>Работа с файлом, хранящим коллекцию.</li><li>Управление коллекцией объектов.</li><li>Назначение автоматически генерируемых полей объектов в коллекции.</li><li>Ожидание подключений и запросов от клиента.</li><li>Обработка полученных запросов (команд).</li><li>Сохранение коллекции в файл при завершении работы приложения.</li><li>Сохранение коллекции в файл при исполнении специальной команды, доступной только серверу (клиент такую команду отправить не может).</li></ul>
<b>Серверное приложение должно состоять из следующих модулей (реализованных в виде одного или нескольких классов):</b>
<ul><li>Модуль приёма подключений.</li><li>Модуль чтения запроса.</li><li>Модуль обработки полученных команд.</li><li>Модуль отправки ответов клиенту.</li></ul>
<p><b>Обязанности клиентского приложения:</b></p>
<ul><li>Чтение команд из консоли.</li><li>Валидация вводимых данных.</li><li>Сериализация введённой команды и её аргументов.</li><li>Отправка полученной команды и её аргументов на сервер.</li><li>Обработка ответа от сервера (вывод результата исполнения команды в консоль).</li><li>Команду <code>save</code> из клиентского приложения необходимо убрать.</li><li>Команда <code>exit</code> завершает работу клиентского приложения.</li></ul>

### 3 этап
<p>Доработать программу из лабораторной работы №6</a> следующим образом:</p>
<ol><li>Организовать хранение коллекции в реляционной СУБД (PostgresQL). Убрать хранение коллекции в файле.</li><li>Для генерации поля id использовать средства базы данных (sequence).</li><li>Обновлять состояние коллекции в памяти только при успешном добавлении объекта в БД</li><li>Все команды получения данных должны работать с коллекцией в памяти, а не в БД</li><li>Организовать возможность регистрации и авторизации пользователей. У пользователя есть возможность указать пароль.</li><li>Пароли при хранении хэшировать алгоритмом <code>SHA-224</code></li><li>Запретить выполнение команд не авторизованным пользователям.</li><li>При хранении объектов сохранять информацию о пользователе, который создал этот объект.</li><li>Пользователи должны иметь возможность просмотра всех объектов коллекции, но модифицировать могут только принадлежащие им.</li><li>Для идентификации пользователя отправлять логин и пароль с каждым запросом.</li></ol>
<p>Необходимо реализовать многопоточную обработку запросов.</p>
<ol><li>Для многопоточного чтения запросов использовать <code>Cached thread pool</code></li><li>Для многопотчной обработки полученного запроса использовать <code>создание нового потока (java.lang.Thread)</code></li><li>Для многопоточной отправки ответа использовать <code>создание нового потока (java.lang.Thread)</code></li><li>Для синхронизации доступа к коллекции использовать <code>java.util.Collections.synchronizedXXX</code></li></ol>

### 4 этап
<ol><li>Интерфейс должен быть реализован с помощью библиотеки JavaFX</li><li>Графический интерфейс клиентской части должен поддерживать <b>русский</b>, <b>норвежский</b>, <b>латвийский</b> и <b>английский (Индия)</b> языки / локали. Должно обеспечиваться корректное отображение чисел, даты и времени в соответстии с локалью. Переключение языков должно происходить без перезапуска приложения. Локализованные ресурсы должны храниться в <b>файле свойств</b>.</li></ol>
<p>Доработать программу из лабораторной работы №7</a> следующим образом:</p>
<p>Заменить консольный клиент на клиент с графическим интерфейсом пользователя(GUI).&nbsp;<br>
В функционал клиента должно входить:</p>
<ol>
	<li>Окно с авторизацией/регистрацией.</li>
	<li>Отображение текущего пользователя.</li>
	<li>Таблица, отображающая все объекты из коллекции
	<ol type="a">
		<li>Каждое поле объекта - отдельная колонка таблицы.</li>
		<li>Строки таблицы можно фильтровать/сортировать по значениям любой из колонок. Сортировку и фильтрацию значений столбцов реализовать с помощью Streams API.</li>
	</ol>
	</li>
	<li>Поддержка всех команд из предыдущих лабораторных работ.</li>
	<li>Область, визуализирующую объекты коллекции
	<ol type="a">
		<li>Объекты должны быть нарисованы с помощью графических примитивов с использованием <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics.html">Graphics</a>, <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/Canvas.html">Canvas</a> или аналогичных средств графической библиотеки.</li>
		<li>При визуализации использовать данные о координатах и размерах объекта.</li>
		<li>Объекты от разных пользователей должны быть нарисованы разными цветами.</li>
		<li>При нажатии на объект должна выводиться информация об этом объекте.</li>
		<li>При добавлении/удалении/изменении объекта, он должен <strong>автоматически</strong> появиться/исчезнуть/измениться&nbsp; на области как владельца, так и всех других клиентов.&nbsp;</li>
		<li>При отрисовке объекта должна воспроизводиться согласованная с преподавателем <strong>анимация</strong>.</li>
	</ol>
	</li>
	<li>Возможность редактирования отдельных полей любого из объектов (принадлежащего пользователю). Переход к редактированию объекта возможен из таблицы с общим списком объектов и из области с визуализацией объекта.</li>
	<li>Возможность удаления выбранного объекта (даже если команды remove ранее не было).</li>
</ol>
