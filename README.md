# srpingrest
someTestApp
----------------------------------------------------------------------------------------
Приложения для Rest API
----------------------------------------------------------------------------------------
Для корректной работы требуется

*MySQL

*HeidiSQL(для наглядной проверки корректности работы с БД)

*Tomcat Apache

*Spring Framework

*Хорошее натсроение :)
----------------------------------------------------------------------------------------
Доступные методы:

1)"/users/{id}" - Получает информацию про определенного пользователя

2)"users/create" - Создает пользователя

3)"setUserOnline/{id}" - Устанавливает статус пользователю с определенным ID статус "Онлайн"

4)"setUserOffline/{id}" - Устанавливает статус пользователю с определенным ID статус "Оффлайн"

5)"getServerStats/{status}?id=" - Статистика пользователей с определенным статусом и необязательным параметром ID

----------------------------------------------------------------------------------------
