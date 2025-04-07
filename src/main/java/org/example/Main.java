package org.example;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Document doc;

        try {
            // fetching the target website
            doc = Jsoup
                .connect("https://www.scrapethissite.com/pages/forms/?page_num=1&per_page=600")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<HockeyTeams> teams = new ArrayList<>();
        Elements hockeyTeamsElements = doc.select("tr.team");

        for (Element hockeyTeamsElement : hockeyTeamsElements) {
            Elements tds = hockeyTeamsElement.select("td");

            HockeyTeams team = new HockeyTeams();
            team.setTeamName(tds.get(0).text());
            team.setYear(Integer.parseInt(tds.get(1).text()));
            team.setWins(Integer.parseInt(tds.get(2).text()));
            team.setLosses(Integer.parseInt(tds.get(3).text()));
            team.setWinRate(Float.parseFloat(tds.get(5).text()));
            team.setGoalsFor(Integer.parseInt(tds.get(6).text()));
            team.setGoalsAgainst(Integer.parseInt(tds.get(7).text()));
            team.setWinLoss(Integer.parseInt(tds.get(8).text()));

            teams.add(team);
        }

        for (HockeyTeams team : teams) {
            System.out.println(
                    team.getTeamName() + " (" + team.getYear() + ") " + "- Wins: " +
                    team.getWins() + ", Losses: " + team.getLosses() +
                    ", Win %: " + team.getWinRate() +
                    ", Goals for: " + team.getGoalsFor() +
                    ", Goals against: " + team.getGoalsAgainst() +
                    ", Win loss: " + team.getWinLoss());
        }

    }
}