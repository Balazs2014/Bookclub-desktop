package com.example.bookclubdesktop;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Statisztika {
    public static List<Member> members = new ArrayList<>();
    public static MemberDb db;

    public static void feltoltes() {
        try {
            db = new MemberDb();
            members = db.getMembers();
        } catch (SQLException e) {
            System.out.println("Nem sikerült kapcsolódni az adatbázishoz");
            System.exit(0);
        }
    }

    public static void kitiltottakSzama() {
        int db = 0;
        for (Member m : members) {
            if (m.isBanned()) {
                db++;
            }
        }
        System.out.printf("Kitiltott tagok száma: %d\n", db);
    }

    public static void vane18Fiatalabb() {
        int i = 0;
        while (i < members.size() && LocalDate.now().getYear() - members.get(i).getBirth_date().getYear() >= 18) {
            i++;
        }
        System.out.printf("%s a tagok között 18 évnél fiatalabb személy.\n", i < members.size() ? "Van" : "Nincs");
    }

    public static void legidosebb() {
        int maxI = 0;
        for (int i = 1; i < members.size(); i++) {
            if (members.get(i).getBirth_date().isBefore(members.get(maxI).getBirth_date())) {
                maxI = i;
            }
        }
        System.out.printf("A legidősebb klubtag: %s (%s)\n", members.get(maxI).getName(), members.get(maxI).getBirth_date());
    }

    public static void tagokszama() {
        int m = 0;
        int f = 0;
        int o = 0;
        for (Member member : members) {
            if (Objects.equals(member.getGender(), null)) {
                o++;
            } else if (Objects.equals(member.getGender(), "M")) {
                m++;
            } else {
                f++;
            }
        }
        System.out.printf("Tagok száma:\n\tNő: %d\n\tFérfi: %d\n\tIsmeretlen: %d\n", f, m,o);
    }

    public static void kivanetiltva() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Adjon meg egy nevet: ");
        String nev = sc.nextLine();
        int i = 0;
        while (i < members.size() && !Objects.equals(members.get(i).getName(), nev)) {
            i++;
        }
        if (i < members.size()) {
            System.out.printf("A megadott személy %s\n", members.get(i).isBanned() ? "ki van tiltva": "nincs kitiltva");
        } else {
            System.out.println("Nincs ilyen tagja a klubnak");
        }
    }

    public static void main(String[] args) {
        feltoltes();
        kitiltottakSzama();
        vane18Fiatalabb();
        legidosebb();
        tagokszama();
        kivanetiltva();
    }
}
