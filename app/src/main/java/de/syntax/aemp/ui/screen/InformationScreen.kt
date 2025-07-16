package de.syntax.aemp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.syntax.aemp.R
import de.syntax.aemp.ui.component.profile.InformationCard

@Composable
fun InformationScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_doppelt_links_48),
                    contentDescription = "Zurück",
                    tint = Color.White
                )
            }
            Text(
                "Information",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
        Text(
            text = "AEMP",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
        Text(
            text = "AEMP steht für Aufbereitungseinheit für Medizinprodukte. Es ist eine Abteilung in Krankenhäusern und Kliniken, die für die Reinigung, Desinfektion, Verpackung und Sterilisation von wiederverwendbaren Medizinprodukten wie Operationsbesteck oder Endoskopen zuständig ist. Die AEMP sorgt dafür, dass diese Instrumente ohne Gefahr für Patienten oder Anwender wiederverwendet werden können.\n\nDie AEMP ist ein wichtiger Bestandteil jeder medizinischen Einrichtung und wird auch als Sterilisationsraum oder \"Steri\" bezeichnet. Die Aufbereitung von Medizinprodukten in der AEMP umfasst folgende Schritte:\n\n• Vorreinigung: Entfernung grober Verschmutzungen.\n• Reinigung: Entfernung organischer und anorganischer Rückstände.\n• Desinfektion: Abtöten von Mikroorganismen.\n• Verpackung: Vorbereitung für die Sterilisation.\n• Sterilisation: Abtöten aller Mikroorganismen, einschließlich Sporen.\n• Dokumentation: Erfassung aller Schritte zur Qualitätssicherung.\n• Freigabe: Kontrolle der Sterilität und Freigabe zur Wiederverwendung.\n\nDie AEMP ist in einen unreinen und einen reinen Bereich unterteilt, um eine Kontamination der bereits aufbereiteten Produkte zu vermeiden. Die Mitarbeiter in der AEMP sind speziell geschult und qualifiziert, um die komplexen Aufbereitungsprozesse korrekt durchzuführen.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
        InformationCard(title = "Vorreinigung", content = "Die Vorreinigung von Medizinprodukten ist ein essenzieller Schritt in der Aufbereitung, bei dem grobe Verschmutzungen, wie Gewebereste oder Materialrückstände, entfernt werden, bevor eine Desinfektion oder Sterilisation erfolgt. Ziel ist es, die Effektivität der folgenden Aufbereitungsschritte sicherzustellen, da Verschmutzungen die Wirkung von Desinfektions- und Sterilisationsmitteln beeinträchtigen können.\n\nDetaillierte Definition und Ablauf:\n• Zweck: Die Vorreinigung dient dazu, grobe Verschmutzungen von Medizinprodukten zu entfernen, die während des Einsatzes am Patienten entstanden sind.\n• Zeitpunkt: Sie sollte unmittelbar nach der Anwendung erfolgen, um ein Eintrocknen der Verschmutzungen zu verhindern.\n• Methoden: Die Vorreinigung kann manuell oder maschinell (z.B. mit Ultraschall) erfolgen. Bei manueller Vorreinigung ist auf eine geeignete Schutzausrüstung und einen Spritzschutz zu achten.\n• Ablauf:\n  - Entfernung grober Verschmutzungen, z.B. mit Zellstoff.\n  - Spülen oder Bürsten der Instrumente unter Flüssigkeitsspiegel.\n  - Bei hartnäckigen Verschmutzungen kann eine Ultraschallreinigung notwendig sein.\n• Wichtigkeit: Eine gründliche Vorreinigung ist die Grundlage für eine effektive Desinfektion und Sterilisation, da Schmutzpartikel die Wirkung der Aufbereitungsmittel beeinträchtigen können.\n• Arbeitsanweisungen: Für die Vorreinigung müssen genaue Arbeitsanweisungen vorliegen, die beschreiben, wie diese durchzuführen ist.\n\nZusammenfassend ist die Vorreinigung ein entscheidender Schritt, um die Sicherheit und Wirksamkeit der weiteren Aufbereitung von Medizinprodukten zu gewährleisten.")
        InformationCard(title = "Reinigung", content = "Die Reinigung von Medizinprodukten ist ein Verfahren zur Entfernung von sichtbaren und unsichtbaren Verschmutzungen, um die Produkte für eine sichere Handhabung oder weitere Aufbereitungsschritte, wie Desinfektion oder Sterilisation, vorzubereiten. Es handelt sich um die erste Stufe der Aufbereitung und dient der Reduktion von Mikroorganismen durch mechanische Entfernung, nicht jedoch deren Abtötung.\n\nMerkmale der Reinigung:\n• Entfernung von Schmutz: Die Reinigung zielt darauf ab, sichtbare und unsichtbare Verschmutzungen zu beseitigen.\n• Mechanische Entfernung: Mikroorganismen werden durch mechanische Kräfte wie Bürsten oder Wasserstrahlen entfernt, aber nicht abgetötet.\n• Vorbereitung für weitere Schritte: Die gereinigten Produkte sind dann für die Desinfektion oder Sterilisation geeignet.\n• Manuelle und maschinelle Verfahren: Es gibt sowohl manuelle Reinigungsverfahren (z.B. mit Bürsten und Reinigungsmitteln) als auch maschinelle Verfahren (z.B. in Reinigungs-Desinfektionsgeräten).\n• Bedeutung für die Hygiene: Eine gründliche Reinigung ist die Grundlage für eine effektive Desinfektion und Sterilisation.\n\nBeispiele für Reinigungsmaßnahmen:\nSpülen mit Wasser und Reinigungsmitteln, Bürsten von Oberflächen und schwer zugänglichen Stellen, Einsatz von Reinigungs-Desinfektionsgeräten (RDG), Zerlegen von Instrumenten in Einzelteile zur Reinigung.\n\nZusammenfassend: Reinigung ist die erste Stufe der Aufbereitung von Medizinprodukten und dient der Entfernung von Schmutz und der Vorbereitung für weitere Aufbereitungsschritte. Eine gründliche Reinigung ist entscheidend für die Effektivität der nachfolgenden Desinfektion oder Sterilisation.")
        InformationCard(title = "Desinfektion", content = "Die Desinfektion von Medizinprodukten ist ein Verfahren zur Reduzierung von Krankheitserregern auf oder in Medizinprodukten, um eine Infektionsgefahr zu minimieren. Ziel ist es, die Anzahl krankheitsauslösender Mikroorganismen so weit zu verringern, dass von den behandelten Produkten keine Infektion mehr ausgehen kann. \n" +
                "\n" +
                "Erläuterung:\n" +
                "* Reduktion von Krankheitserregern:\u2028Bei der Desinfektion werden Mikroorganismen wie Bakterien, Viren und Pilze abgetötet oder inaktiviert, um die Infektionsgefahr zu minimieren. \u2028Medizinprodukte:\u2028Die Desinfektion betrifft alle Arten von Medizinprodukten, die mit Patienten oder Behandlungsbereichen in Kontakt kommen, wie z.B. Instrumente, Geräte oder Oberflächen. \u2028Infektionsprävention:\u2028Durch die Desinfektion wird das Risiko einer Übertragung von Krankheitserregern auf den Patienten oder das medizinische Personal reduziert, was zur Infektionsprävention beiträgt. \u2028Abgrenzung zur Sterilisation:\u2028Die Desinfektion unterscheidet sich von der Sterilisation dadurch, dass nicht alle Mikroorganismen abgetötet werden müssen, sondern eine signifikante Reduktion ausreicht, um die Infektionsgefahr zu minimieren. \u2028Verschiedene Methoden:\u2028Es gibt verschiedene Methoden der Desinfektion, wie z.B. die chemische Desinfektion mit Desinfektionsmitteln oder die thermische Desinfektion durch Hitze. \u2028\u2028Wichtige Aspekte bei der Desinfektion von Medizinprodukten:\n" +
                "* Auswahl des richtigen Desinfektionsmittels:\u2028Das Desinfektionsmittel muss für das jeweilige Medizinprodukt geeignet sein und eine ausreichende Wirksamkeit gegen die relevanten Mikroorganismen aufweisen. \u2028Korrekte Anwendung:\u2028Das Desinfektionsmittel muss in der richtigen Konzentration und Einwirkzeit angewendet werden, um eine optimale Wirksamkeit zu gewährleisten. \u2028Hygieneplan:\u2028Die Desinfektion von Medizinprodukten sollte in einem umfassenden Hygieneplan geregelt sein, der die verschiedenen Schritte der Aufbereitung beschreibt. \u2028Dokumentation:\u2028Die Desinfektionsmaßnahmen sollten dokumentiert werden, um die Rückverfolgbarkeit zu gewährleisten und die Einhaltung der Hygienevorschriften zu belegen. \u2028Die Desinfektion von Medizinprodukten ist ein wesentlicher Bestandteil der Krankenhaushygiene und trägt maßgeblich zur Patientensicherheit bei. \n")
        InformationCard(title = "Verpackung", content = "Die Vorbereitung für die Sterilisation medizinischer Produkte durch Verpackung bezieht sich auf den Prozess, bei dem sterile Medizinprodukte so verpackt werden, dass sie vor Kontamination durch Mikroorganismen geschützt sind. Dies wird erreicht, indem eine mikrobielle Barriere geschaffen wird, die während der Lagerung und bis zur Verwendung intakt bleibt. \n" +
                "\n" +
                "Erläuterung:\n" +
                "Die Verpackung von Sterilgut ist ein kritischer Schritt im Aufbereitungsprozess, um sicherzustellen, dass Medizinprodukte bis zur Verwendung keimfrei bleiben und das Risiko von Infektionen minimiert wird. Die Verpackung muss bestimmte Anforderungen erfüllen: \n" +
                "\n" +
                "* Schutz vor Kontamination:\u2028Die Verpackung muss eine Barriere gegen das Eindringen von Bakterien, Pilzen und anderen Mikroorganismen bilden. \u2028Materialauswahl:\u2028Die Verpackungsmaterialien (z.B. spezielle Folien, Papier, oder Container) müssen für das gewählte Sterilisationsverfahren geeignet sein und eine Dampfdurchlässigkeit gewährleisten, wenn feuchte Hitze (Autoklavierung) verwendet wird. \u2028Integrität:\u2028Die Verpackung muss während der Lagerung und Handhabung intakt bleiben, um ihre Schutzfunktion zu gewährleisten. \u2028Kennzeichnung:\u2028Die Verpackung muss Informationen über das Sterilisationsverfahren, das Verfallsdatum und andere relevante Daten tragen. \u2028Eignung:\u2028Die Verpackung muss auf das jeweilige Medizinprodukt und das angewendete Sterilisationsverfahren abgestimmt sein. \u2028\u2028Zusammenfassend lässt sich sagen, dass die Verpackung von Medizinprodukten vor der Sterilisation ein wesentlicher Schritt zur Sicherstellung ihrer Sterilität ist und speziell ausgewählte Materialien und Techniken erfordert, um eine sichere Anwendung zu gewährleisten. \n")
        InformationCard(title = "Sterilisation", content = "Die Sterilisation von Medizinprodukten ist ein Prozess, bei dem alle lebensfähigen Mikroorganismen, einschließlich Bakterien, Viren, Pilze und deren Sporen, von Medizinprodukten entfernt oder abgetötet werden, um eine vollständige Keimfreiheit zu erreichen. Ziel ist es, die Übertragung von Krankheitserregern durch die Medizinprodukte zu verhindern. \n" +
                "\n" +
                "Erläuterung:\n" +
                "* Mikroorganismen:\u2028Die Sterilisation zielt darauf ab, eine Vielzahl von Mikroorganismen zu eliminieren, die in verschiedenen Formen (vegetativ oder als Sporen) auf Medizinprodukten vorhanden sein können. \u2028Keimfreiheit:\u2028Das Ergebnis der Sterilisation ist ein Zustand der Keimfreiheit, bei dem die theoretische Wahrscheinlichkeit, einen lebensfähigen Keim auf einem Produkt zu finden, sehr gering ist (z.B. 1:1.000.000 nach DIN EN 556). \u2028Medizinprodukte:\u2028Die Sterilisation betrifft Medizinprodukte, also Instrumente, Geräte oder Materialien, die in der Medizin eingesetzt werden und mit Patienten in Kontakt kommen. \u2028Hygienemaßnahme:\u2028Die Sterilisation ist eine wichtige Hygienemaßnahme, um das Infektionsrisiko bei der Anwendung von Medizinprodukten zu minimieren. \u2028Verschiedene Methoden:\u2028Es gibt verschiedene Methoden zur Sterilisation, darunter Dampfsterilisation, Heißluftsterilisation, Strahlensterilisation (z.B. mit Gamma- oder Elektronenstrahlen), Plasmasterilisation und Gassterilisation. \u2028\u2028Zusammenfassend lässt sich sagen, dass die Sterilisation von Medizinprodukten ein kritischer Prozess ist, um die Sicherheit von Patienten zu gewährleisten, indem sie eine vollständige Elimination von Mikroorganismen sicherstellt. ")
        InformationCard(title = "Dokumentation", content = "Die Technische Dokumentation für Medizinprodukte ist eine Zusammenstellung aller relevanten Dokumente, die Hersteller von Medizinprodukten bereitstellen müssen. Sie ist Voraussetzung für die Konformitätsbewertung und somit für die Zulassung eines Medizinprodukts. Die Technische Dokumentation dient als Nachweis, dass das Medizinprodukt die grundlegenden Sicherheits- und Leistungsanforderungen der Medizinprodukteverordnung (MDR) erfüllt. ")
        InformationCard(title = "Freigabe", content = "Die Freigabe von Medizinprodukten ist der Prozess, bei dem nach der Aufbereitung, also Reinigung, Desinfektion oder Sterilisation, die Erlaubnis zur erneuten Anwendung oder Lagerung des Medizinprodukts erteilt wird. Dies geschieht, nachdem sichergestellt wurde, dass das Produkt den vorgegebenen Qualitätsstandards entspricht und keine Gefährdung für Patienten oder Personal darstellt. \n" +
                "\n" +
                "Erläuterung:\n" +
                "Die Freigabe ist ein wichtiger Schritt in der Aufbereitung von Medizinprodukten, um sicherzustellen, dass diese nach der Aufbereitung wieder sicher und effektiv eingesetzt werden können. Hierbei werden verschiedene Aspekte überprüft: \n" +
                "\n" +
                "* Prozesskontrolle:\u2028Es wird sichergestellt, dass der gesamte Aufbereitungsprozess korrekt durchgeführt wurde, einschließlich Reinigung, Desinfektion und gegebenenfalls Sterilisation. \u2028Qualitätskontrolle:\u2028Das Medizinprodukt wird auf seine Funktionsfähigkeit, Unversehrtheit und Sauberkeit geprüft. \u2028Dokumentation:\u2028Die Freigabe wird in der Regel dokumentiert, um die ordnungsgemäße Aufbereitung nachzuweisen. \u2028Personelle Verantwortung:\u2028Die Freigabe darf nur von entsprechend qualifiziertem Personal durchgeführt werden, das über die notwendigen Kenntnisse und Fähigkeiten verfügt. \u2028\u2028Die Freigabe von Medizinprodukten ist ein essenzieller Bestandteil der Infektionsprävention und trägt maßgeblich zur Patientensicherheit bei. \n")
    }
}