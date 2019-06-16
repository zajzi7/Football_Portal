package pl.dominik.football.services;


import pl.dominik.football.domain.entity.Menu;

public interface MenuService {

    void saveMenu(Menu menu);

    void addMenu(String title);

    void addSubMenu(String title, int parentId);

}
