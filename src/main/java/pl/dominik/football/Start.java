package pl.dominik.football;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.dominik.football.services.AdminConfigService;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.NewsService;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.TeamService;

import java.time.LocalDate;

@Component
public class Start implements CommandLineRunner {

    @Autowired
    AdminConfigService adminConfigService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    RoundService roundService;

    @Autowired
    MatchService matchService;

    @Autowired
    TeamService teamService;

//    @Autowired
//    PlayerService playerService;

    @Autowired
    NewsService newsService;

    @Override
    public void run(String... args) throws Exception {

        //manual creating round just for tests
        roundService.createRound(4, LocalDate.of(2019, 5, 19));
        roundService.createRound(5, LocalDate.of(2019, 5, 26));
        roundService.createRound(6, LocalDate.of(2019, 6, 2));

        //manual creating season just for tests
        seasonService.createSeason("2018/2019");
        seasonService.createSeason("2019/2020");
        seasonService.addRound(
                roundService.getRoundByInt(4),
                seasonService.getSeason("2018/2019"));
        seasonService.addRound(
                roundService.getRoundByInt(5),
                seasonService.getSeason("2019/2020"));
        seasonService.addRound(
                roundService.getRoundByInt(6),
                seasonService.getSeason("2018/2019"));

        adminConfigService.createAdminConfig();
        adminConfigService.setCurrentSeason(seasonService.getSeason("2018/2019"));

        //manual creating players just for tests
//        playerService.createPlayer("Dominik", "Drabina");
//        playerService.createPlayer("Zdzislaw", "Mruk");

        String team1 = "Husaria Łapczyca";
        String team2 = "Czarni Kobyle";
        String team3 = "Naprzód Sobolów";
        String team4 = "Wisła Grobla";
        String team5 = "Korona Brzeźnica";

        //manual creating teams just for tests
        teamService.createTeam(team1);
        teamService.createTeam(team2);
        teamService.createTeam(team3);
        teamService.createTeam(team4);
        teamService.createTeam(team5);

        String src1 = "https://s2.fbcdn.pl/5/clubs/53115/logos/s/herb-rywala-koronabrzeznica_67.jpg";
        String src2 = "https://s2.fbcdn.pl/8/clubs/48148/logos/s/herb-rywala-czarnikobyle1_29.jpg";
        String src3 = "https://s2.fbcdn.pl/3/clubs/67153/herby/149/67153.jpg";
        String src4 = "https://s2.fbcdn.pl/8/clubs/5698/logos/s/herb-rywala-kspogoria_65.jpg";
        String src5 = "https://s2.fbcdn.pl/5/clubs/53115/logos/s/herb-rywala-koronabrzeznica_102.jpg";

        teamService.setCrest(teamService.getTeamByName(team1), src1);
        teamService.setCrest(teamService.getTeamByName(team2), src2);
        teamService.setCrest(teamService.getTeamByName(team3), src3);
        teamService.setCrest(teamService.getTeamByName(team4), src4);
        teamService.setCrest(teamService.getTeamByName(team5), src5);

        adminConfigService.setFavouriteTeam(teamService.getTeamByName(team1));

        //add teams to season
        seasonService.addTeam(teamService.getTeamByName(team1), seasonService.getSeason("2018/2019"));
        seasonService.addTeam(teamService.getTeamByName(team2), seasonService.getSeason("2018/2019"));
        seasonService.addTeam(teamService.getTeamByName(team3), seasonService.getSeason("2018/2019"));
        seasonService.addTeam(teamService.getTeamByName(team4), seasonService.getSeason("2018/2019"));
        seasonService.addTeam(teamService.getTeamByName(team5), seasonService.getSeason("2018/2019"));

        //manual creating matches just for tests
        matchService.addMatchResult(
                teamService.getTeamByName("Husaria Łapczyca"),
                teamService.getTeamByName("Czarni Kobyle"),
                3,2,
                roundService.getRoundByInt(4));

        matchService.addMatchResult(
                teamService.getTeamByName("Naprzód Sobolów"),
                teamService.getTeamByName("Wisła Grobla"),
                1, 1,
                roundService.getRoundByInt(4));

        matchService.addMatchResult(
                teamService.getTeamByName("Wisła Grobla"),
                teamService.getTeamByName("Husaria Łapczyca"),
                1,4,
                roundService.getRoundByInt(4));

        //News
        String img = "https://scontent.fktw1-1.fna.fbcdn.net/v/t1.0-9/23131009_1484385974929930_8871563576500883025_n.jpg?_nc_cat=102&_nc_oc=AQm_kB04O_v8FVbvtGpB7j7Y4d_R0AthN_b9MCNBpG5lRK40hZdEAl3OalzB-2tMpBE&_nc_ht=scontent.fktw1-1.fna&oh=aed94163710411736872fb13e9dbaefa&oe=5E198F35";
        String img2 = "https://scontent.fktw1-1.fna.fbcdn.net/v/t1.0-9/31717004_1664771746891351_4132714512572743680_n.jpg?_nc_cat=110&_nc_oc=AQl0wvDa3w9FVliU7k3cABsM7T21DgtTSX_YCI4OylIaWkkxemSpwt7RzjGeyE8JwBY&_nc_ht=scontent.fktw1-1.fna&oh=32f034602c92a206aadf76e074c3220f&oe=5E2742EB";
        String img3 = "https://scontent.fktw1-1.fna.fbcdn.net/v/t1.0-9/32423170_1675675165801009_3312244930037940224_n.jpg?_nc_cat=102&_nc_oc=AQn0FNtIOTkm8R3psZBWDo-NevOXyPXMEyld-jL_1MN8y2dMXaKFDQgEIUoJSDSsB3U&_nc_ht=scontent.fktw1-1.fna&oh=089a29e55af65029fe1b9d5dc742ed22&oe=5E1D4CD5";
        newsService.addNews("Wygrana 3:0!", img,"<b>Litwo! Ojczyzno moja! Ty jesteś jak zdrowie. </b>Nazywał się wypyta o książki nowe dziwo w grób się dziś nagodzi do bębna tęsknił<!--more-->, siedząc w porządnym domu, i nie ma jutro na dzień postrzegam, jak mnie dziecko przestraszone we śnie. Podróżny do Podkomorzanki. Nie zmienia czy moda i stają mu biło nadzwyczajnie. Więc było wyłożyć koszt na pagórku niewielkim, we śnie. Podróżny zląkł ich lekkości woły właśnie i każdego wodza legijonu i napój w drewnianej szafie poznał z nim padnie. Dalej Jasiński, młodzian piękny i nowych gości. W ślad widać z Warszaw mam list, to mówiąc, że się kołem. W ślad widać z kształtu, jeśli zechcesz, i widać nóżki na ścianach wisiały. Tu śmiech młodzieży mowę Wojskiego też same widzi sprzęty, też same portrety na nowo pytania. Cóż złego, że zamczysko wzięliśmy w takim Litwinka tylko się i, z krzykiem podróżnego barwą spłonęła rumian jak zaraza. Przecież nieraz dziad żebrzący chleba gałeczki trzy osoby na Ojczyzny łono. Tymczasem na wychowanie poznano stołeczne. To nie skąpił. On opowiadał, jako swe rozkazy i Hrabia z całej ozdobi widzę mniej krzykliwy i swoją ważność.");
        newsService.addNews("Słaba dyspozycyjność na wyjeździe", img2,"<b>Drogi Marszałku, Wysoka Izbo. PKB rośnie.</b> Różnorakie i znaczenia tych problemów nie trzeba udowadniać, ponieważ<!--more--> skoordynowanie pracy obu urzędów spełnia istotną rolę w większym stopniu tworzenie postaw uczestników wobec zadań stanowionych przez organizację. Sytuacja która miała miejsce szkolenia kadry odpowiadającego potrzebom. Z drugiej strony, zakończenie tego projektu zmusza nas do przeanalizowania kierunków rozwoju. Z pełną odpowiedzialnością mogę stwierdzić iż dalszy rozwój różnych form działalności organizacyjnej zabezpiecza udział szerokiej grupie w restrukturyzacji przedsiębiorstwa. Nie chcę państwu niczego sugerować, ale zakup nowego sprzętu zmusza nas do przeanalizowania kierunków rozwoju. Podobnie, rozszerzenie naszej działalności organizacyjnej zabezpiecza udział szerokiej grupie w restrukturyzacji przedsiębiorstwa. Gdy za 4 lata. Reasumując. skoordynowanie pracy obu urzędów wymaga sprecyzowania i określenia systemu spełnia istotną rolę w wypracowaniu odpowiednich warunków aktywizacji. Proszę państwa, zakup nowego sprzętu pomaga w określaniu dalszych kierunków postępowego wychowania. Jednakowoż, realizacja określonych zadań stanowionych przez organizację. Dalszy rozwój różnych form oddziaływania. Troska organizacji, a także rozpoczęcie powszechnej akcji kształtowania podstaw zabezpiecza udział szerokiej grupie w przygotowaniu i unowocześniania istniejących kryteriów wymaga niezwykłej precyzji w kształtowaniu systemu powszechnego uczestnictwa. Praca wre. Sytuacja która miała miejsce szkolenia kadr.");
        newsService.addNews("Mecz w ciężkich warunkach pogodowych", img3,"<b>Początek traktatu czasu panowania Fryderyka Wielkiego</b>, Króla Pruskiego żył w jego predykatów Zupełność w jego pozorne szczęście jest<!--more--> to, co my nabywamy o rumie czyli drogą do jego gramatyce, osobliwie gdy by był naruszony, tedyby to dowód z siebie samego i świat zjawiły. Jest on jednak względem losu sobie pomyśleć można. Co? czy ten stosunek ustanie, a jednak się szczęśliwości godnemi szczęśliwości, ale oraz wiele za mierzydło do czynu; i darów Bożych. Więc musiemy mu w pojecie o przedmiotach, mają z cnotliwych powodów być prawdziwie cnotliwemi. Gdyby człowiek w moralności z przyczyny wiecznej męki, albowiem obfity zapas środków i poczciwości wagę daje. Coż to co zaś pochodzące z przyrodzenia przestaje na najemniczą płacę czyli samoistne. Ale te rzeczy i nieczuły zostawał, tedy znajdziemy, że sama przez się teraz do uszczęśliwienia; dla tego jest tylko na największą część jego łaski lub przyrachowane. W takim razie przestały być na przestawaniu na ograniczeniu dobrego. Zło niema być przyczytane; ale to jest ta: każda realność, która karami medicynalnemi Te trzy pojęcia przeprzeć, lub oczyszczenia ich są pragmatyczne kary. Więc to myślenie we wszystkich rzeczy. Na czymże będzie niespokojny. Wzór lub przykrością, której dążność sama przez co najwięcej objecuje, jest przyczytanie dzieje się w takowym przyszło znosić niewygody i nieodmiennym; więc dobremu nadane; ale Dobro znowu się ustawą być zelżywemi lub wprost jak Kopczyński i te są pragmatyczne, albo ie on zawsze wewnętrzną odmianę w niemieckim rękopiśmie. Te trzy korekty czytać. Przypominam też niemożemy Dokładne lub moralnym, ponieważ z zapytaniem owym: czemuż nie inna jaka istota. Więc to najwyższe dobro. Albowiem samowiedza pokazuje żem ja substancyą tj. do świętej ustawy sprawowali, co się spodziewać, że my z posłuchu spisywane. Z jedyności Dobra niemoże być przyczytane. O przyczytaniu musi być przyczytane. W takim razie upodla się trafią gdzie nam być wynagrodzony, że całkiem oznaczona, gdyż się stać się sam jestem istotą i gdyby kto zapyta: skąd złe sprawy czynili, może być Istność jest pożyteczno, toby się nam tu w nauce o Dobru, która ją można więc pochodzą przykrości plagi i sposobności, lecz kiedy się być przedmiotami jego rzeczywistość, nazywa się. Żaden człowiek, który szczęśliwość otrzymać ma; albowiem inaczej każdy pojąć może. Gdy więc wkradło się zarodku do innego ontologicznego predykatu Istności pierwiastkowej Istności najrealniejszej czyli wiadomością i niemożna było powinności nie byłoby to sposób myślenia i w.");

        for (int i = 0; i < 20; i++) {
            newsService.addNews("Wygrana 3:0! Nr: " + i, img,"<b>Litwo! Ojczyzno moja! Ty jesteś jak zdrowie. </b>Nazywał się wypyta o książki nowe dziwo w grób się dziś nagodzi do bębna tęsknił<!--more-->, siedząc w porządnym domu, i nie ma jutro na dzień postrzegam, jak mnie dziecko przestraszone we śnie. Podróżny do Podkomorzanki. Nie zmienia czy moda i stają mu biło nadzwyczajnie. Więc było wyłożyć koszt na pagórku niewielkim, we śnie. Podróżny zląkł ich lekkości woły właśnie i każdego wodza legijonu i napój w drewnianej szafie poznał z nim padnie. Dalej Jasiński, młodzian piękny i nowych gości. W ślad widać z Warszaw mam list, to mówiąc, że się kołem. W ślad widać z kształtu, jeśli zechcesz, i widać nóżki na ścianach wisiały. Tu śmiech młodzieży mowę Wojskiego też same widzi sprzęty, też same portrety na nowo pytania. Cóż złego, że zamczysko wzięliśmy w takim Litwinka tylko się i, z krzykiem podróżnego barwą spłonęła rumian jak zaraza. Przecież nieraz dziad żebrzący chleba gałeczki trzy osoby na Ojczyzny łono. Tymczasem na wychowanie poznano stołeczne. To nie skąpił. On opowiadał, jako swe rozkazy i Hrabia z całej ozdobi widzę mniej krzykliwy i swoją ważność.");
            newsService.addNews("Słaba dyspozycyjność na wyjeździe", img2,"<b>Drogi Marszałku, Wysoka Izbo. PKB rośnie.</b> Różnorakie i znaczenia tych problemów nie trzeba udowadniać, ponieważ<!--more--> skoordynowanie pracy obu urzędów spełnia istotną rolę w większym stopniu tworzenie postaw uczestników wobec zadań stanowionych przez organizację. Sytuacja która miała miejsce szkolenia kadry odpowiadającego potrzebom. Z drugiej strony, zakończenie tego projektu zmusza nas do przeanalizowania kierunków rozwoju. Z pełną odpowiedzialnością mogę stwierdzić iż dalszy rozwój różnych form działalności organizacyjnej zabezpiecza udział szerokiej grupie w restrukturyzacji przedsiębiorstwa. Nie chcę państwu niczego sugerować, ale zakup nowego sprzętu zmusza nas do przeanalizowania kierunków rozwoju. Podobnie, rozszerzenie naszej działalności organizacyjnej zabezpiecza udział szerokiej grupie w restrukturyzacji przedsiębiorstwa. Gdy za 4 lata. Reasumując. skoordynowanie pracy obu urzędów wymaga sprecyzowania i określenia systemu spełnia istotną rolę w wypracowaniu odpowiednich warunków aktywizacji. Proszę państwa, zakup nowego sprzętu pomaga w określaniu dalszych kierunków postępowego wychowania. Jednakowoż, realizacja określonych zadań stanowionych przez organizację. Dalszy rozwój różnych form oddziaływania. Troska organizacji, a także rozpoczęcie powszechnej akcji kształtowania podstaw zabezpiecza udział szerokiej grupie w przygotowaniu i unowocześniania istniejących kryteriów wymaga niezwykłej precyzji w kształtowaniu systemu powszechnego uczestnictwa. Praca wre. Sytuacja która miała miejsce szkolenia kadr.");
            newsService.addNews("Mecz w ciężkich warunkach pogodowych", img3,"<b>Początek traktatu czasu panowania Fryderyka Wielkiego</b>, Króla Pruskiego żył w jego predykatów Zupełność w jego pozorne szczęście jest<!--more--> to, co my nabywamy o rumie czyli drogą do jego gramatyce, osobliwie gdy by był naruszony, tedyby to dowód z siebie samego i świat zjawiły. Jest on jednak względem losu sobie pomyśleć można. Co? czy ten stosunek ustanie, a jednak się szczęśliwości godnemi szczęśliwości, ale oraz wiele za mierzydło do czynu; i darów Bożych. Więc musiemy mu w pojecie o przedmiotach, mają z cnotliwych powodów być prawdziwie cnotliwemi. Gdyby człowiek w moralności z przyczyny wiecznej męki, albowiem obfity zapas środków i poczciwości wagę daje. Coż to co zaś pochodzące z przyrodzenia przestaje na najemniczą płacę czyli samoistne. Ale te rzeczy i nieczuły zostawał, tedy znajdziemy, że sama przez się teraz do uszczęśliwienia; dla tego jest tylko na największą część jego łaski lub przyrachowane. W takim razie przestały być na przestawaniu na ograniczeniu dobrego. Zło niema być przyczytane; ale to jest ta: każda realność, która karami medicynalnemi Te trzy pojęcia przeprzeć, lub oczyszczenia ich są pragmatyczne kary. Więc to myślenie we wszystkich rzeczy. Na czymże będzie niespokojny. Wzór lub przykrością, której dążność sama przez co najwięcej objecuje, jest przyczytanie dzieje się w takowym przyszło znosić niewygody i nieodmiennym; więc dobremu nadane; ale Dobro znowu się ustawą być zelżywemi lub wprost jak Kopczyński i te są pragmatyczne, albo ie on zawsze wewnętrzną odmianę w niemieckim rękopiśmie. Te trzy korekty czytać. Przypominam też niemożemy Dokładne lub moralnym, ponieważ z zapytaniem owym: czemuż nie inna jaka istota. Więc to najwyższe dobro. Albowiem samowiedza pokazuje żem ja substancyą tj. do świętej ustawy sprawowali, co się spodziewać, że my z posłuchu spisywane. Z jedyności Dobra niemoże być przyczytane. O przyczytaniu musi być przyczytane. W takim razie upodla się trafią gdzie nam być wynagrodzony, że całkiem oznaczona, gdyż się stać się sam jestem istotą i gdyby kto zapyta: skąd złe sprawy czynili, może być Istność jest pożyteczno, toby się nam tu w nauce o Dobru, która ją można więc pochodzą przykrości plagi i sposobności, lecz kiedy się być przedmiotami jego rzeczywistość, nazywa się. Żaden człowiek, który szczęśliwość otrzymać ma; albowiem inaczej każdy pojąć może. Gdy więc wkradło się zarodku do innego ontologicznego predykatu Istności pierwiastkowej Istności najrealniejszej czyli wiadomością i niemożna było powinności nie byłoby to sposób myślenia i w.");
        }

//        newsService.addNews("Mecz w ciężkich warunkach pogodowych", img4,"<b>Początek traktatu czasu panowania Fryderyka Wielkiego</b>, Króla Pruskiego żył w jego predykatów Zupełność w jego pozorne szczęście jest<!--more--> to, co my nabywamy o rumie czyli drogą do jego gramatyce, osobliwie gdy by był naruszony, tedyby to dowód z siebie samego i świat zjawiły. Jest on jednak względem losu sobie pomyśleć można. Co? czy ten stosunek ustanie, a jednak się szczęśliwości godnemi szczęśliwości, ale oraz wiele za mierzydło do czynu; i darów Bożych. Więc musiemy mu w pojecie o przedmiotach, mają z cnotliwych powodów być prawdziwie cnotliwemi. Gdyby człowiek w moralności z przyczyny wiecznej męki, albowiem obfity zapas środków i poczciwości wagę daje. Coż to co zaś pochodzące z przyrodzenia przestaje na najemniczą płacę czyli samoistne. Ale te rzeczy i nieczuły zostawał, tedy znajdziemy, że sama przez się teraz do uszczęśliwienia; dla tego jest tylko na największą część jego łaski lub przyrachowane. W takim razie przestały być na przestawaniu na ograniczeniu dobrego. Zło niema być przyczytane; ale to jest ta: każda realność, która karami medicynalnemi Te trzy pojęcia przeprzeć, lub oczyszczenia ich są pragmatyczne kary. Więc to myślenie we wszystkich rzeczy. Na czymże będzie niespokojny. Wzór lub przykrością, której dążność sama przez co najwięcej objecuje, jest przyczytanie dzieje się w takowym przyszło znosić niewygody i nieodmiennym; więc dobremu nadane; ale Dobro znowu się ustawą być zelżywemi lub wprost jak Kopczyński i te są pragmatyczne, albo ie on zawsze wewnętrzną odmianę w niemieckim rękopiśmie. Te trzy korekty czytać. Przypominam też niemożemy Dokładne lub moralnym, ponieważ z zapytaniem owym: czemuż nie inna jaka istota. Więc to najwyższe dobro. Albowiem samowiedza pokazuje żem ja substancyą tj. do świętej ustawy sprawowali, co się spodziewać, że my z posłuchu spisywane. Z jedyności Dobra niemoże być przyczytane. O przyczytaniu musi być przyczytane. W takim razie upodla się trafią gdzie nam być wynagrodzony, że całkiem oznaczona, gdyż się stać się sam jestem istotą i gdyby kto zapyta: skąd złe sprawy czynili, może być Istność jest pożyteczno, toby się nam tu w nauce o Dobru, która ją można więc pochodzą przykrości plagi i sposobności, lecz kiedy się być przedmiotami jego rzeczywistość, nazywa się. Żaden człowiek, który szczęśliwość otrzymać ma; albowiem inaczej każdy pojąć może. Gdy więc wkradło się zarodku do innego ontologicznego predykatu Istności pierwiastkowej Istności najrealniejszej czyli wiadomością i niemożna było powinności nie byłoby to sposób myślenia i w.");

        adminConfigService.setImportantMessageFlag(false);
        adminConfigService.setImportantMessageContent("Uwaga! Z powodu kiepskiego stanu boiska w wyniku opadów atmosferycznych dzisiejszy mecz zostaje przeniesiony. Więcej informacji później.");

    }
}
