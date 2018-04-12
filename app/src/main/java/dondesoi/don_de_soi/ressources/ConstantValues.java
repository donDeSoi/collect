package dondesoi.don_de_soi.ressources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dondesoi.don_de_soi.R;

/**
 * Created by Vitaly on 11/16/2017.
 * storage of constant values
 */

public class ConstantValues {

    //used in info activity
    //used for the list of different gives
    // https://www.agence-biomedecine.fr/Site-pour-le-grand-public?lang=fr
    private static final String BLOOD = "don de sang";
    private static final String PLASMA = "don de plasma";
    private static final String PLAQUETTES = "don de plaquettes";
    private static final String ORGANES = "don d'organes et de tissus";
    private static final String MOELLE = "don de moelle osseuse";
    private static final String OVOCITES = "don d'ovocytes";
    private static final String SPERM = "don de spermatozoïdes";

    public final static List<String> getGivesList(){
        List<String> givesList = new ArrayList<String>();
        givesList.add("");
        givesList.add(BLOOD);
        givesList.add(PLASMA);
        givesList.add(PLAQUETTES);
        givesList.add(ORGANES);
        givesList.add(MOELLE);
        givesList.add(OVOCITES);
        givesList.add(SPERM);
        return givesList;
    }

    public final static List<String> getInfoList(){
        List<String> infoList = new ArrayList<String>();
        infoList.add("");
        infoList.add(BLOOD_INFOS);
        infoList.add(PLASMA_INFOS);
        infoList.add(PLAQUETTES_INFOS);
        infoList.add(ORGANNES_INFOS);
        infoList.add(MOELLE_INFOS);
        infoList.add(OVOCITES_INFOS);
        infoList.add(SPERM_INFOS);
        return infoList;
    }

    public static final int DATE_INDEX = 0, MONTH_INDEX = 1, DAY_INDEX = 2;
    public static final String SEPARATOR = "-";
    public static final String INDEX_LIST = "index";
    public static final int DIFF_MONTH_NEW_GIVE = 1;
    public static final String FILE_NAME = "don.txt", APP_NAME = "don de soi", NOTIFICATION_CONTENT = "vous pouvez faire un nouveau don";
    public static final int MONTH_OFFSET = 1;
    public static final String DEFAULT_DATE = "2017-10-0";

    //used for the preferences
    public static final String NOTIFICATIONS = "notifications", LOCALISATION = "localisation", DATE = "date", VIBRATE = "vibrate";

    public static final double DEFAULT_LATITUDE = 48, DEFAULT_LONGITUDE = 2;
    public static final int BIG_ZOOM = 8, MID_ZOOM = 5, SMALL_ZOOm = 2;
    private ConstantValues(){
        throw new RuntimeException();
    }

    //used in searchcenter activity
    public static final String LIEN_A_PARTAGER = "https://dondesang.efs.sante.fr/";

    // DB EN DUR
    public static final List<Center> CENTER_LISY = Arrays.asList(
            new Center(1,48.8370792,2.365042899999935,
                    "Hôpital Pitié-Salpétrière 12 rue Bruant Ou 47 Bd Hôpital - Pavillon Laveran 75013 Paris 13ème",
                    "Sang, plasma, plaquettes","0142160252\n Chevaleret (ligne 6) ou Saint-Marcel (ligne 5)\n Bus 57, 91, 27",
                    "Ouvertures : du lundi au samedi de 9h à 15h30"
                    ),
            new Center(2,48.8719527,2.3677867000000106,
                    "Hôpital Saint-Louis 38 rue Bichat 75010 Paris 10ème", "Sang, plasma, plaquettes",
                    "0153722250\n" + "Goncourt (ligne 11) , Colonel Fabien (ligne 2), République, Jacques Bonsergent (ligne 5)\n" + "Bus 46, 75",
                    "Ouvertures : lundi 8h-16h / du mardi au vendredi 8h-18h / samedi 8h-15h30"
                    ),
            new Center(3, 48.8461866,2.3815067000000454,
                    "21 rue Crozatier 75012 Paris 12ème","Sang, plasma, plaquettes",
                    "0153029200\n" + "Reuilly Diderot (lignes 1 ou 8)\n" + "Bus 57",
                    "Ouvertures : lundi 8h-16h / du mardi au vendredi 8h-18h / samedi 8h-15h30"
                    ),
            new Center( 4,48.8761483,2.3330965000000106,"Site de Prélèvement de la Trinité 55 rue de Châteaudun 75009 Paris 9ème",
                    "Sang, plasma, plaquettes",
                    "0155316060\n" + "Trinité d’Estienne d’Orves (ligne 12)\n" + "Bus : 26, 32, 43, 68, 81",
                    "Ouvertures : lundi 8h30-16h / du mardi au vendredi 8h30-18h / samedi 8h30-16h"
                    ),
            new Center( 5,48.8490608,2.3019768999999997,"6 rue Alexandre Cabanel 75015 Paris 15ème",
                    "Sang, plasma, plaquettes",
                    "0145669717\n" + "Cambronne (ligne 6)\n" + "Bus : 80",
                    "Ouvertures : lundi 8h-16h / mardi, jeudi, vendredi 8h-18h / mercredi 7h30-18h / samedi 8h-15h30"
                    ),
            new Center(6, 48.89817900000001,2.3319499000000405,"Hôpital Bichat 46 rue Henri Huchard - Secteur Claude Bernard 75018 Paris 18ème",
                    "Sang, plasma, plaquettes","0140258180\n" + "Porte de Saint-Ouen (ligne 13)\n" + "Bus : 81, PC 3",
                    "Ouvertures : du lundi au vendredi 10h-16h30 / samedi 8h30-15h"
                    ),
            new Center(7,48.8388508,2.2740327999999863,"Hôpital Européen Georges Pompidou 20 rue Leblanc - Rdc du Hall A 75015 Paris 15ème",
                    "0153782170\n" + "Balard (ligne 8) ou RER C (arrêt : Bld Victor)\n" + "Tramway : T3\n" + "Bus : PC1, 42",
                    "Sang, plasma","Ouvertures : lundi, mercredi, vendredi, samedi 9h-15h30 / mardi, jeudi 10h30-17h"
                    ),
            new Center(8, 48.90812030000001,2.310465600000043,
                    "Hôpital Beaujon - 100 bld du Général Leclerc Secteur Bleu - Porte 15 - Niveau -1 92110 Clichy",
                    "Sang, plasma, plaquettes","0140875902\n" + "Mairie de Clichy (ligne 13)\n" + "Bus : 74",
                    "Ouvertures : du lundi au samedi 8h30-15h"
                    ),
            new Center(9, 48.9145819,2.4239873999999872,
                    "Hôpital Avicenne - 125 rue de Stalingrad - Secteur Orange - Porte 9 - 93000 Bobigny",
                    "Sang, plasma, plaquettes","0148955679\n" + "La Courneuve (ligne 7) ou Bobigny-Pablo Picasso (ligne 5)\n" + "Tramway : T1 (arrêt : Hospital Avicenne)",
                    "Ouvertures du lundi au samedi : 9h-15h30"
                    ),
            new Center(10,48.7981135,2.450942599999962,"Site Créteil l'Echat 1 Voie Félix Eboué 94010 Créteil",
                    "Sang, plasma, plaquettes","0156727650\n" + "Créteil-l'Echat (ligne 8)",
                    "Ouvertures : du lundi au vendredi 9h-15h30 / samedi 8h30-15h"
                    ),
            new Center(11,48.8304689,2.122798800000055,"Site de Prélèvement du Chesnay 2 Rue Jean-Louis Forain 78153 Le Chesnay",
                    "Sang, plasma, plaquettes",
                    "0139234538\n" + "SNCF : Versailles Rive Droite, Rive Gauche ou Chantier\n" + "Bus : A, H (Hôp. Mignot) ou B, T express (Parly 2) ou S (Les Comptesses)",
                    "Ouvertures du lundi au vendredi : 8h30-15h \n" + "Samedi : 8h -14h30"
                    ),
            new Center(12,48.9191164,2.0222352000000683,"Hôpital Poissy 9 rue Champ Gaillard 78300 Poissy",
                    "Sang, plasma, plaquettes",
                    "0139222550\n" + "RER A\n" + "Bus : 51 ou 52 - Arrêt Hôpital",
                    "Ouvertures : lundi, mardi, mercredi, vendredi 9h-15h30 / jeudi 12h30-19h / samedi 8h30-15h"
                    ),
            new Center(13,48.6313179,2.414797799999974,"Rue du Pont Amar Quartier du Canal 91080 Courcouronnes",
                    "Sang, plasma, plaquettes","0160780818\n" + "RER D - Gares d'Evry-Courcouronnes ou d'Orangis-Bois de l'Epine\n" + "Bus : 402, 404",
                    "Ouvertures : du lundi au vendredi 9h-15h30 / samedi (semaines paires) 9h-15h30"
                    ),
            new Center(14,49.0653832,2.0950659000000087,"Avenue de l'Ile de France 95301 Cergy Pontoise",
                    "Sang, plasma, plaquettes","RER A (Cergy Préfecture) + bus 42 (Hôp. René Dubos) ou RER C (Pontoise) + bus 34N (Hôp. René Dubos)",
                    "Ouvertures du lundi au samedi : 9h-15h30 "
                    )
    );
    public final static List<Integer> geticonlist(){
        List<Integer> picList = new ArrayList<Integer>();
        picList.add(0);
        picList.add(R.drawable.donsang);
        picList.add(R.drawable.donplasma);
        picList.add(R.drawable.donplaquettes);
        picList.add(R.drawable.donorganes);
        picList.add(R.drawable.bone);
        picList.add(R.drawable.oocyte);
        picList.add(R.drawable.sperm);
        return picList;
    }

    public final static List<Integer> getpiclist(){
        List<Integer> picList = new ArrayList<Integer>();
        picList.add(R.drawable.menudondesoi);
        picList.add(R.drawable.blooddonation);
        picList.add(R.drawable.plasmadonation);
        picList.add(R.drawable.plateletdonation);
        picList.add(R.drawable.organdonation);
        picList.add(R.drawable.bonedonation);
        picList.add(R.drawable.oocytedonation);
        picList.add(R.drawable.spermdonation);
        return picList;
    }

    //used in detailed infos
    private static final String BLOOD_INFOS = "\nLes dons de sang permettent d'aider beaucoup de malades." +
            "\nLe besoin est estimé à 10000 dons par jour soit 5000 L de sang en moyenne." +
            "\nVous devez avoir entre 18 et 70 ans et peser plus de 50 kg pour donner.\n" +
            "Vous pouvez donner votre sang en vous rendant dans un centre de dons ou à une collecte sans prendre de rendez-vous.";
    private static final String PLASMA_INFOS = "\nLe plasma est un composant du sang : il est sa partie liquide. Vous devez prendre rendez-vous dans un centre de dons pour en donner." +
            "\nLe plasma contient des protéines nécessaires pour certains malades (Polytraumatisés, grands brûlés, hémophiles etc ...)" +
            "\n90% du plasma prélevé est destiné à la fabrication de médicaments." +
            "\n\nInformations tirées de dondesang.efs.santé.fr";
    private static final String PLAQUETTES_INFOS = "\nLes plaquettes sont un composant du sang. Elles peuvent donc être extraites de dons du sang. Mais pour subvenir aux besoins importants il faut en prélever spécifiquement." +
            "\nLe besoin est estimé à 500 dons de plaquettes par jour. Les plaquettes ont une durée de vie faible (5 jours) et les besoins sont donc réguliers." +
            "\nLes plaquettes sont utilisées notamment pour contrer certaines maladies (leucémies) ou des traitements lourds (chimiothérapies) qui peuvent empêcher la fabrication de cellules sanguines." +
            "\n\nInformations tirées de dondesang.efs.santé.fr";
    private static final String ORGANNES_INFOS = "\nIl n'existe pas de contre indication (âge, état de santé) au don d'organes et de tissus. Les médecins évaluent les organes au cas par cas pour s'assurer de la qualité de la greffe réalisée." +
            "\nDepuis 1976 la loi fait de chaque français un donneur d'organes et de tissus présumé. Pour ne pas donner il faut s'inscrire sur le registre national des refus." +
            "\nConcernant les dons de reins seul un membre de la famille proche du receveur peut donner de son vivant" +
            "\n\nInformations tirées de agence-biomédecine.fr";
    private static final String MOELLE_INFOS = "\nSeules les personnes entre 18 ans et 51 ans peuvent donner de la moelle. Il faut également être en parfaite santé." +
            "\nLe don de moelle osseuse permet de soigner des maladies graves du sang." +
            "\nLa moelle osseuse assure la production des cellules hématopoïdiques, c'est à dire des cellules qui sont à l'origine des cellules sanguines. On peut la prélever dans les os du bassin ou via une prise de sang précédée d'un traitement médicamenteux" +
            "\n Pour donner, il faut s'inscrire sur le registre des donneurs volontaires de moelle osseuse" +
            "\n\nInformations tirées de agence-biomédecine.fr";
    private static final String OVOCITES_INFOS = "\nLes dons d'ovocytes concernent les femmes de moins de 37 ans et en parfaite santé." +
            "\nCes ovocytes permettent d'avoir des enfants à des femmes qui ne produisent pas naturellement d'ovocytes ou dont les ovocytes présentent des anomalies." +
            "\nOn peut donner en se rendant dans un centre spécialisé." +
            "\nLe don se déroule en 3 étapes : " +
            "\n     1) Une préparation où la donneuse échange avec les médecins" +
            "\n     2) La stimmulation ovarienne qui dure entre 10 et 12 jours" +
            "\n     3) Le prélèvement effectué dans un hôpital." +
            "\n\nInformations tirées de agence-biomédecine.fr";
    private static final String SPERM_INFOS = "\n\nLes dons de spermatozoïdes concernent les hommes agés de moins de 45 ans en parfaite santé. Les spermatozoïdes sont donnés à des hommes n'en produisent pas naturellement ou dont les spermatozoïdes présentent une anomalie" +
            "\n     1) Une préparation où le donneur échange avec les médecins" +
            "\n     2) Le premier receuil s'effectue après 3 à 5 jours d'abstinence et permet d'évaluer les spermatozoïdes du donneur" +
            "\n     3) La congélation et les recueils suivants qui seront utilisés pour le don." +
            "\n\nInformations tirées de agence-biomédecine.fr";
}