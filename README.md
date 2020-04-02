# Catalogue-search

Projekt utworzony w środowisku Intellij, JAVA w wersji 1.8, GUI zaprojektowane w JAVAFX.

Program umożliwiający listowanie plików z katalogu(z podkatalogami), obsługę podglądu plików tekstowych oraz obrazów. Program umożliwia również uruchamianie innych plików(np pdf, mp), jednak nie następuje to w oknie programu.

Aplikacja została użyta z wykorzystaniem słabych referencji, pliki są ładowane do klasy FileItem, następnie tworzona jest struktura WeakHashMap(), gdzie jako klucz podaje się ścieżkę do pliku w formacie String.

W folderze release znajdują się paczki uruchomieniowe
