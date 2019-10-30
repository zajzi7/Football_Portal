//package pl.dominik.football.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import pl.dominik.football.domain.entity.Menu;
//import pl.dominik.football.domain.repository.MenuRepository;
//
//@Service
//public class MenuServiceImpl implements MenuService {
//
//    @Autowired
//    MenuRepository menuRepository;
//
//    @Override
//    public void saveMenu(Menu menu) {
//        menuRepository.save(menu);
//    }
//
//    @Override
//    public void addMenu(String title) {
//        Menu menu = new Menu(title);
//        menuRepository.save(menu);
//    }
//
//    @Override
//    public void addSubMenu(String title, int parentId) {
//        Menu menu = new Menu(title, parentId);
//        menuRepository.save(menu);
//    }
//
//
//}
