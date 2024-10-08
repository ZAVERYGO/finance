
# Общее пояснение

Данный проект в первую очередь разработан для закрепления принципов проектирования и разработки бэкенда для современных веб-приложений.

На курсе было изучено:

- Работа c **HTTP**-протоколом:
    - Различия между **GET**, **POST**, **PUT**, **DELETE** запросами.
    - Способы передачи данных: **Query string**, **Headers**, **Body**
    - Принципы формирования URL по рекомендациям **RESTful API**
- Работа с **Базами данных** на примере **PostgreSQL**:
    - Различия между **DDL** и **DML**
    - **Транзакции**
    - Различие подходов разработки при использовании **JDBC** и **ORM**
- Работа с применением MVC что подразумевает разделение на слои
    - **Controller** - слой доступа в котором была реализована работа по протоколу **HTTP** через использование библиотеки **Spring Web MVC** внутри которого работает **DispatcherServlet**
    - **Service** - слой сервиса в котором была реализована бизнес логика и конвертации из **DTO** в **Entity**
    - **Dao** - слой доступа к данным в котором была реализована работа с **PostgreSQL** через использование библиотеки **Spring Data JPA**
- Работа с **Maven**
    - **pom.xml** - как файл с метаинформацией о проекте хранящий информацию о зависимостях и этапах сборки проекта
    - Использование тэга **property** для создания переменных которые можно использовать в pom.xml
    - Автоматизированный запуск **JUnit** тестов через **maven-surefire-plugin**
    - Автоматический деплой **WAR** файла с использованием плагина **tomcat7-maven-plugin** и команды **mvn clean package tomcat7:deploy**
    - Использование **parent pom** для сборки нескольких **Maven проектов** подряд
- Работа с **Git**
    - **.gitignore** - файл в корневой директории репозитория Git, который указывает, какие файлы и директории должны быть проигнорированы системой контроля версий. Это полезно для исключения из репозитория временных файлов, артефактов сборки, настроек среды разработки и других файлов, которые не должны отслеживаться в системе контроля версий.
    - **Branch** - Ветки позволяют изолировать работу над новыми функциями, исправлениями ошибок или экспериментами, не влияя на основную кодовую базу. Основная ветка, как правило, называется main или master, но можно создавать дополнительные ветки для других задач.
    - **Pull Request** - то запрос на внесение изменений из одной ветки в другую в рамках системы управления версиями, обычно в хостингах репозиториев, таких как GitHub, GitLab или Bitbucket. Pull Request позволяет участникам проекта обсудить изменения, провести код-ревью и протестировать код перед его объединением с основной веткой.
    - **Merge Conflict** - возникает, когда Git не может автоматически объединить изменения из разных веток из-за противоречивых изменений в одних и тех же строках одного и того же файла. В таких случаях необходимо вручную разрешить конфликт, выбрав, какие изменения сохранить.
- Работа с **Docker**
    - **Dockerfile** - Как файл, содержащий набор инструкций для создания Docker-образа. Он определяет, какие шаги необходимо выполнить для установки нужного программного обеспечения и его конфигурации. Основные инструкции:   
      - **FROM** - Указывает базовый образ, на основе которого будет строиться новый образ. Это обязательная инструкция, с которой начинается любой Dockerfile.  
      - **RUN** - Выполняет команды в процессе сборки образа. Обычно используется для установки пакетов и выполнения других операций настройки.  
      - **CMD** - Задает команду, которая будет выполняться при запуске контейнера. В отличие от RUN, она не выполняется во время сборки образа, а только при запуске контейнера.  
      - **COPY** - Копирует файлы и директории с компьютера, где осуществляется сборка образа в файловую систему контейнера.  
      - **ADD** - Похожа на COPY, но имеет дополнительные возможности, такие как разархивирование локальных файлов и загрузка файлов из URL.  
      - **WORKDIR** - Задает рабочую директорию для последующих инструкций RUN, CMD, ENTRYPOINT, COPY и ADD.  
      - **ENV** - Устанавливает переменные окружения, которые будут доступны в контейнере.
    - **Image** (образ) - это неизменяемый шаблон, который содержит все, что нужно для запуска приложения: собранное приложение и настройки. Образ создается на основе инструкций из Dockerfile. Образы могут быть сохранены в реестре образов (например, Docker Hub) и многократно использоваться для создания контейнеров.
      - Для создания образа из **Dockerfile** находясь в папке с файлом выполнить команду в консоли: `docker build -t %укажите желаемое название образа% .`
    - **Container** (контейнер) - это экземпляр образа, запущенный в изолированном окружении. Контейнеры используют ресурсы хоста (CPU, память, файловую систему) и обеспечивают изоляцию приложений друг от друга. Контейнеры позволяют легко развертывать, масштабировать и переносить приложения между различными средами.
      - Для создания контейнера из ранее созданного образа выполнить команду в консоли `docker run %название образа придуманное на предыдущем этапе%`
      - Часто указывают следующие опции:
        - `--name` - указывают имя образа. Например: `app_name:version`
        - `-p` - указывают порт доступа к контейнеру. Например: `80:8080`
        - `-e` - указывают значения для энварьмент переменных. Например: `НАЗВАНИЕ_ПЕРЕМЕННОЙ=ЗНАЧЕНИЕ ПЕРЕМЕННОЙ`
    - **Compose** - это инструмент для определения и запуска многоконтейнерных Docker-приложений. С помощью файла docker-compose.yml можно описать конфигурацию всех сервисов, которые будут работать вместе (например, веб-сервер, база данных, кэш). Docker Compose упрощает управление и запуск комплексных приложений.
- Работа с **OpenAPI**
  - **paths** - Описывает пути (endpoints) и операции (методы HTTP) для взаимодействия с API. Это ключевая часть спецификации, где определяется, как клиенты могут взаимодействовать с сервером.
  - **components** - Содержит переиспользуемые части спецификации, такие как схемы данных, параметры, ответы и т.д.
  - **security** - Описывает схемы безопасности, используемые для защиты API. Может включать такие механизмы, как OAuth2, API ключи и базовая аутентификация.
  - **tags** - Список тегов для упрощения навигации и группировки операций в документации API.

# Используемые технологии и фреймворки:
- [Java 21](https://docs.oracle.com/en/java/javase/17/docs/api/)
- [Maven](https://maven.apache.org/)
- [Spring Boot X.X.X](https://spring.io/projects/spring-boot)
- [Spring Data JPA X.X.X](https://spring.io/projects/spring-data-jpa)
- [Spring Security X.X.X](https://spring.io/projects/spring-security)
- [Spring Cloud X.X.X](https://spring.io/projects/spring-cloud)
- [MinIO](https://min.io/)
- [PostgreSQL](https://www.postgresql.org/)
- [Docker](https://www.docker.com/)
- [Apache Kafka](https://kafka.apache.org/)
- [Redis](https://redis.io/)
# Описание проекта: Управление финансами
Разработан проект, состоящий из четырёх микросервисов, реализованных по архитектуре RESTful. Микросервисы эффективно взаимодействуют друг с другом с помощью Feign-клиентов для обеспечения функциональности системы, связанной с управлением пользователями и финансовыми операциями.

user-service: Микросервис для работы с пользователями, обеспечивающий регистрацию, аутентификацию и управление профилями пользователей. Он взаимодействует с другими микросервисами для проверки и управления пользовательскими данными через Feign-клиенты.

classifier-service: Микросервис, отвечающий за работу с валютами и классификацией операций, поддерживающий различные типы операций, которые могут быть выполнены на счёте. Этот сервис использует Feign для получения информации о пользователях и их операциях из других микросервисов.

account-service: Микросервис для управления банковскими счетами и операциями. Он эффективно взаимодействует с user-service для валидации пользователей и с classifier-service для получения информации о типах операций.

audit-service: Микросервис, предназначенный для записи действий в системе, что позволяет обеспечивать безопасность и контроль за операциями пользователей. Он собирает данные из всех других микросервисов с помощью Feign-клиентов для создания полного аудита действий.

message-service: Микросервис, предназначенный для отправки сообщений на почту.

В проекте используется Apache Kafka для отправки сообщений из микросервиса user-service в message-service. Это позволяет реализовать асинхронное взаимодействие, что улучшает производительность и масштабируемость системы. 
Redis используется для хранения кодов верификации пользователей. Каждый код хранится с установленным временем жизни (TTL), что позволяет автоматически удалять устаревшие коды и предотвращать их повторное использование.
Вся система развернута в контейнерах Docker, управляемых с помощью Docker Compose. Для обработки входящих запросов и перенаправления их к соответствующим микросервисам используется Nginx в качестве обратного прокси-сервера.



