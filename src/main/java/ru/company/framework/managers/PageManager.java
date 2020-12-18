package ru.company.framework.managers;

import ru.company.framework.pages.DepositPage;
import ru.company.framework.pages.StartPage;

public class PageManager {
    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;

    /**
     * Стартовая страничка
     */
    StartPage startPage;

    /**
     * Стартовая страничка вклада
     */
    DepositPage depositPage;

    /**
     * Конструктор специально запривейтили (синглтон)
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManage
     *
     * @return PageManage
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link StartPage}
     *
     * @return StartPage
     */
    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    /**
     * Ленивая инициализация {@link DepositPage}
     *
     * @return DepositPage
     */
    public DepositPage getDepositPage() {
        if (depositPage == null) {
            depositPage = new DepositPage();
        }
        return depositPage;
    }
}
