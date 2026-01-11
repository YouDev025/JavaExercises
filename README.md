# 📚 Projet de Formation Java

Bienvenue dans mon dépôt d'apprentissage Java ! Ce projet contient mes exercices, pratiques et projets réalisés durant ma formation en programmation Java.

## 🗂️ Structure du Projet
```
My_PROJECTS/
├── .idea/                  # Configuration IntelliJ IDEA
├── out/                    # Fichiers compilés
├── practice/               # Dossier principal de pratique
│   ├── src/
│   │   ├── algorithms/     # Algorithmes et structures de données
│   │   ├── basics/         # Fondamentaux de Java
│   │   ├── data_struct/    # Exercices sur les structures de données
│   │   ├── functions/      # Pratique des fonctions/méthodes
│   │   ├── oop/            # Programmation Orientée Objet
│   │   └── projects/       # Projets complets
│   │       └── Main.java
│   └── practice.iml        # Configuration du module
├── .gitignore
└── README.md               # Ce fichier
```

## 📖 Contenu par Dossier

### 🔤 basics/
Concepts fondamentaux de Java :
- Variables et types de données primitifs
- Opérateurs arithmétiques et logiques
- Structures de contrôle (if, else, switch)
- Boucles (for, while, do-while)
- Entrées/sorties console avec Scanner
- Manipulation de chaînes de caractères (String)

### 📊 data_struct/
Exercices pratiques sur les structures de données :
- **AverageCalculator.java** - Calcul de moyennes
- **CalculateurCercle.java** - Calculs géométriques (périmètre, aire)
- **MaxArrayFinder.java** - Recherche du maximum dans un tableau
- **MINArrayFinder.java** - Recherche du minimum dans un tableau
- **StringReverser.java** - Inversion de chaînes de caractères
- **StudentGradeManager.java** - Gestion des notes d'étudiants
- **TemperatureAnalyzer.java** - Analyse de données de température

### 🔧 functions/
Pratique des méthodes :
- Déclaration et appel de méthodes
- Paramètres et valeurs de retour
- Surcharge de méthodes (overloading)
- Méthodes statiques vs d'instance
- Portée des variables (scope)
- Méthodes récursives

### 🎯 algorithms/
Algorithmes classiques :
- Algorithmes de tri (bubble sort, selection sort, insertion sort)
- Algorithmes de recherche (linéaire, binaire)
- Manipulation avancée de tableaux
- Complexité algorithmique (Big O)

### 🏗️ oop/
Programmation Orientée Objet :
- Classes et objets
- Constructeurs
- Encapsulation (getters/setters)
- Héritage et super
- Polymorphisme
- Interfaces et classes abstraites
- Modificateurs d'accès (public, private, protected)

### 🚀 projects/
Projets complets intégrant plusieurs concepts :
- Applications console interactives
- Mini-systèmes de gestion
- Exercices de synthèse

## 🛠️ Prérequis

- **Java JDK** : Version 11 ou supérieure
- **IDE recommandé** : IntelliJ IDEA (Community ou Ultimate)
- **Git** : Pour la gestion de versions
- **Système d'exploitation** : Windows, macOS ou Linux

## ▶️ Comment Utiliser ce Projet

### 1. Cloner le dépôt
```bash
git clone https://github.com/votre-username/votre-repo.git
cd My_PROJECTS
```

### 2. Ouvrir avec IntelliJ IDEA
1. Lancer IntelliJ IDEA
2. **File → Open**
3. Sélectionner le dossier `My_PROJECTS`
4. Attendre l'indexation et la configuration automatique du projet

### 3. Exécuter un fichier
- **Méthode 1** : Clic droit sur le fichier `.java` → **Run 'NomDuFichier'**
- **Méthode 2** : Ouvrir le fichier et utiliser :
    - Windows/Linux : `Shift + F10`
    - macOS : `Ctrl + R`
- **Méthode 3** : Cliquer sur l'icône ▶️ verte à côté de la méthode `main`

### 4. Compiler manuellement (optionnel)
```bash
javac practice/src/data_struct/AverageCalculator.java
java -cp practice/src data_struct.AverageCalculator
```

## 📝 Conventions de Code

### Nommage
- **Classes** : `PascalCase` (ex: `StudentGradeManager`, `TemperatureAnalyzer`)
- **Méthodes** : `camelCase` (ex: `calculateAverage`, `findMaxValue`)
- **Variables** : `camelCase` (ex: `studentName`, `totalScore`)
- **Constantes** : `SCREAMING_SNAKE_CASE` (ex: `MAX_SIZE`, `PI`)
- **Packages** : `lowercase` (ex: `algorithms`, `datastructures`)

### Bonnes Pratiques
- ✅ Utiliser des noms de variables descriptifs
- ✅ Commenter les algorithmes complexes
- ✅ Une classe = un fichier
- ✅ Indentation cohérente (4 espaces)
- ✅ Gérer les exceptions appropriées

## 🎯 Objectifs d'Apprentissage

- [x] Maîtriser la syntaxe de base de Java
- [x] Manipuler les tableaux et collections
- [x] Créer et utiliser des méthodes
- [ ] Comprendre la POO en profondeur
- [ ] Implémenter des algorithmes de tri et recherche
- [ ] Gérer les exceptions correctement
- [ ] Utiliser les collections Java (ArrayList, HashMap, etc.)
- [ ] Créer des projets fonctionnels complets

## 📊 Progression

| Module | Progression | Fichiers | Statut |
|--------|-------------|----------|--------|
| **basics** | 80% | - | 🟢 Maîtrisé |
| **data_struct** | 60% | 7 fichiers | 🟢 En cours |
| **functions** | 40% | - | 🟡 En développement |
| **algorithms** | 20% | - | 🟡 Démarré |
| **oop** | 30% | - | 🟡 En apprentissage |
| **projects** | 15% | Main.java | 🔴 À développer |

**Légende** : 🟢 Actif | 🟡 En cours | 🔴 Planifié | ✅ Terminé

## 📚 Ressources Utiles

### Documentation Officielle
- [Oracle Java Documentation](https://docs.oracle.com/javase/tutorial/)
- [Java API Reference](https://docs.oracle.com/en/java/javase/11/docs/api/)

### Tutoriels
- [W3Schools Java](https://www.w3schools.com/java/)
- [OpenClassrooms - Java](https://openclassrooms.com/fr/courses/6173501-apprenez-a-programmer-en-java)
- [GeeksforGeeks Java](https://www.geeksforgeeks.org/java/)

### Communauté
- [Stack Overflow - Java](https://stackoverflow.com/questions/tagged/java)
- [Reddit - r/learnjava](https://www.reddit.com/r/learnjava/)



## 🚀 Prochaines Étapes

1. Compléter les exercices sur les algorithmes de tri
2. Approfondir la POO avec des projets pratiques
3. Implémenter des design patterns courants
4. Créer une application console complète (calculatrice, gestionnaire de tâches, etc.)
5. Explorer les collections Java (ArrayList, LinkedList, HashMap)

## 🤝 Contributions

Ce projet est personnel et à but éducatif. Cependant :
- Les **suggestions** sont les bienvenues via les issues
- Les **corrections** peuvent être proposées via pull requests
- Le **feedback** est toujours apprécié !

## 📄 Licence

Ce projet est à usage **éducatif personnel**. Libre d'utilisation pour l'apprentissage.


---

### 💡 Citation du Jour
*"Le code est comme l'humour. Quand vous devez l'expliquer, c'est mauvais."* – Cory House

**Happy Coding!** 💻✨