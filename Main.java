/*Разобрать самостоятельно и решить задачи на терминальные методы StreamAPI
1) Метод collect() Преобразовать стрим из пациентов в Map, где ключ- дата рождения, а значение- фио без преобразований
2) Метод foreach() Вывести для каждого пациента на экран собранную через String.format() строку, содержащую поля id, fio, birthdate, sex, company в читаемом виде
3) Методы min() и max()
- Получить «максимального» пациента из потока.
Параметр «большести» - Возраст (нужно написать компаратор – см урок)
- Получить «минимального» пациента из потока.
Параметр большести- ФИО- компаратор, который сначала сравнивает по фамилии, потом по имени, потом по отчеству.
4) Метод findFirst()
Получить первого пациента, кто родился в декабре 1999 года
5) Метод allMatch()
Проверить, что в переданной в метод компании есть хотя бы один мужчина старше 25 лет
6) Метод noneMatch()
Проверить, есть ли хоть один человек, старше 100 лет
7) Метод anyMatch()
Проверить, есть ли хоть один человек, старше 100 лет
*/
import java.util.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.Month;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        List<Patient> patients = Dump.getDump();

/*        // 1) Метод collect()
        Map<LocalDate, String> birthDateToFioMap = patients.stream()
                .collect(Collectors.toMap(Patient::getBirthDate, Patient::getFio));

        System.out.println("1) Дата рождения - ФИО:");
        birthDateToFioMap.forEach((key, value) -> System.out.println(key + " - " + value));

        Map<LocalDate, String> patientMap = patients.stream()
                .collect(Collectors.toMap(
                        Patient::getBirthDate,
                        Patient::getFullName,
                        (existingValue, newValue) -> existingValue + " and " + newValue
                ));
*/
        // 2) Метод foreach()
        System.out.println("\n2) Информация о пациентах:");
        patients.forEach(patient -> {
            System.out.println(String.format("ID: %d, ФИО: %s, Дата рождения: %s, Пол: %s, Компания: %s",
                    patient.getId(), patient.getFio(), patient.getBirthDate(), patient.getSex(), patient.getCompany()));
        });

        // 3) Методы min() и max()
        Comparator<Patient> ageComparator = Comparator.comparingInt(p -> Period.between(p.getBirthDate(), LocalDate.now()).getYears());
        Comparator<Patient> nameComparator = Comparator.comparing(Patient::getFio);

        Patient oldestPatient = patients.stream()
                .max(ageComparator)
                .orElse(null);

        Patient youngestPatient = patients.stream()
                .min(nameComparator)
                .orElse(null);

        System.out.println("\n3) Самый старший пациент: " + oldestPatient);
        System.out.println("Самый молодой пациент: " + youngestPatient);

        // 4) Метод findFirst()
        Patient december1999Patient = patients.stream()
                .filter(patient -> patient.getBirthDate().getYear() == 1999 && patient.getBirthDate().getMonth() == Month.DECEMBER)
                .findFirst()
                .orElse(null);

        System.out.println("\n4) Пациент, родившийся в декабре 1999 года: " + december1999Patient);

        // 5) Метод allMatch()
        boolean hasMaleOver25 = patients.stream()
                .anyMatch(patient -> patient.getSex().equals("муж") && Period.between(patient.getBirthDate(), LocalDate.now()).getYears() > 25);

        System.out.println("\n5) В компании есть мужчина старше 25 лет: " + hasMaleOver25);

        // 6) Метод noneMatch()
        boolean hasOver100YearsOld = patients.stream()
                .anyMatch(patient -> Period.between(patient.getBirthDate(), LocalDate.now()).getYears() > 100);

        System.out.println("\n6) Есть ли человек старше 100 лет: " + hasOver100YearsOld);

        // 7) Метод anyMatch()
        boolean hasOver100YearsOldAny = patients.stream()
                .anyMatch(patient -> Period.between(patient.getBirthDate(), LocalDate.now()).getYears() > 100);

        System.out.println("\n7) Есть ли хоть один человек старше 100 лет: " + hasOver100YearsOldAny);
    }
}