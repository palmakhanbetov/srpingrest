# srpingrest
someTestApp
----------------------------------------------------------------------------------------
Приложения для Rest API
----------------------------------------------------------------------------------------
Для корректной работы требуется

*MySQL
----------------------------------------------------------------------------------------
Создание таблицы USER_TBL:
CREATE TABLE USER_TBL(id NUMBER primary key, FIRSTNAME varchar2(50), LASTNAME varchar2(50), BIRTHDATE date, STATUS NUMBER)

*HeidiSQL(для наглядной проверки корректности работы с БД)

*Tomcat Apache

*Хорошее натсроение :)

*Spring Framework
----------------------------------------------------------------------------------------
Доступные методы:

1)"/users/{id}" - Получает информацию про определенного пользователя

2)"users/create" - Создает пользователя

3)"setUserOnline/{id}" - Устанавливает статус пользователю с определенным ID статус "Онлайн"

4)"setUserOffline/{id}" - Устанавливает статус пользователю с определенным ID статус "Оффлайн"

5)"getServerStats/{status}?id=" - Статистика пользователей с определенным статусом и необязательным параметром ID

----------------------------------------------------------------------------------------
