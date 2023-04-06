# Changelog after 1st Code Review Session

### [FEATURE]       Save Date of Birth instead of Age and calculate Age accordingly.
- [x] `[Angular]`   Save DoB instead of Age. Still show age aswell. Customer model updated.
- [x] `[Angular]`   Calculating Age implemented in Customer.service.
- [x] `[Angular]`   Updated List-Customers and Detail-Customer components to show DoB and Age.
- [x] `[Java]`      Save DoB instead of Age. Customer entity updated.
- [x] `[Java]`      Fix Unit and Integration tests after implementing Date of Birth.

### [FEATURE]       Datepicker Widget Component for Date fields.
- [x] `[Angular]`   Added @ng-bootstrap/ng-bootstrap for DatePicker widgets. (ng add)
- [x] `[Angular]`   Implementation of NgbDatepicker for Adding- and Editing Customers' Date of Birth.
- [x] `[Angular]`   Implementation of Type-Conversion between Java Date and NgbDate Types.

### [BUGFIX]        Testing and Compliancy
- [x] `[Angular]`   Restructured Directories and Components into Angular Modules. (core, shared, user, customer)
- [x] `[Java]`      Use Random Port for testing Spring-Boot.
- [x] `[Java]`      Fixed Service Interface / Impl Folder structure. (i.e. services/CustomerService and Impl.)
- [x] `[Java]`      Fixed/Rewritten Deprecated code.
- [x] `[Java]`      Return correct HTTP code on update address api call. (OK, not CREATED)
- [x] `[Java]`      Migrate from JUnit 4 to JUnit 5.
- [x] `[JUnit]`     Make unit-test folder structure compliant.
- [x] `[JUnit]`     Fixed testcases broken after Address Class implementation.
- [x] `[JUnit]`     Write Address unit tests.
- [x] `[JUnit]`     Use @Before to Arrange data for test-cases.
- [x] `[Maven]`     Removed Spring-Migrator Dependency.

### [FEATURE]       Address Object instead of Strings *[BRANCH:AddressTypeMigration]*
- [x] `[Angular]`   Implementation of Address model and updating codebase.
- [x] `[Angular]`   Updated app-components touching Customer.Address.
- [x] `[Angular]`   Merge (AddressTypeMigration) to (master)
- [x] `[Java]`      Implementation of Address class and updating codebase.
- [x] `[Java]`      Implementation of Address Validators. Make basic checks to verify NL Address format.
- [x] `[Java]`      Implementation of JPA changes to reflect new Database Structure.
- [x] `[Java]`      Merge (AddressTypeMigration) to (master)
- [x] `[JUnit]`     Unit Test-Cases for Address Validators.

### [FEATURE]       Improved ErrorResponse handling and feedback to user.
- [x] `[Angular]`   Catch and show Validator Error Messages.
- [x] `[Angular]`   Display Auth and SecurityFilter errors properly.
- [x] `[Angular]`   App-Wide integration of ErrorResponseUtilService to unwrap ResponseBody Validation- and Error-Messages.
- [x] `[Java]`      Send Validator Error Messages back to client. (For Front-End FieldValidation)
- [x] `[Java]`      Add additional ExceptionHandlers to catch all Field Validation Errors.
- [x] `[Java]`      AuthManager / SecurityFilter now returning errors wrapped in ErrorResponse.
- [x] `[Java]`      Check Validator Messages for spelling and consistency.

### [SECURITY]      Security improvements.
- [x] `[Angular]`   Automatically end the session and logout when invalid/expired JWT token is detected.
- [x] `[Angular]`   ErrorResponseHelperUtil now gracefully handles null-references.
- [x] `[Angular]`   Implemented AuthGuard to Module RoutingModules to prevent unauthorized front-end component access.
- [x] `[Angular]`   Implemented Token verification in AuthGuard.
- [x] `[Java]`      Clean-Up SecurityConfig.
- [x] `[Java]`      (Docker Only Issue) Fix CORS(?) blocking access to swagger-ui in Docker.
- [x] `[Java]`      CVE-2018-1000873 CWE-20 - Fixed in jackson-datatype-jsr310 dependency upgrade.

### [FEATURE]       Various UI Improvements
- [x] `[Angular]`   Now using Bootstrap 5.2's 'form-floating' and 'form-control' formats for all Customer Details forms.
- [x] `[Angular]`   Add Labels in Details-Customer component.
- [x] `[Angular]`   Reactive Server-Side Field Validation with Field Highlighting and Response Messages.

### [DOCKER]        Dockerized Submodules and Composed Environment
- [x] `[Angular]`   Multi-Staged Dockerfile and dockerignore. Builds from scratch. Pulls most recent source from GitHub.
- [x] `[Java]`      Multi-Staged Dockerfile and dockerignore. Builds from scratch. Pulls most recent source from GitHub.
- [x] `[Docker]`    Docker Compose End-to-End Implementation. Only Docker required to run the docker-compose
- [x] `[GitHub]`    Created Compose-CustomerDetailsService Repo with Java- and Angular-CDS as SubModules.