package com.example.projetfinal_tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Déclaration des variables. On s'assure que la partie débute avec le joueur X.
    private var joueurCourant = "X"
    private var nomJoueurX = ""
    private var nomJoueurO = ""
    private var pointageX = 0
    private var pointageO = 0
    private var partieEnCours = false
    // Drapeau permettant de pouvoir débuter une nouvelle partie
    // sans perdre les données des joueurs
    private var nomsEntres = false

    private lateinit var carre1: TextView
    private lateinit var carre2: TextView
    private lateinit var carre3: TextView
    private lateinit var carre4: TextView
    private lateinit var carre5: TextView
    private lateinit var carre6: TextView
    private lateinit var carre7: TextView
    private lateinit var carre8: TextView
    private lateinit var carre9: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Association des variables à leur contrôle respectif.
        val nom = findViewById<EditText>(R.id.edtNom)
        val joueurX = findViewById<TextView>(R.id.txtJoueurX)
        val joueurO = findViewById<TextView>(R.id.txtJoueurO)
        val start = findViewById<Button>(R.id.btnStart)

        // Initialisation du bouton.
        start.text = if (nomsEntres) "Recommencer la partie" else "Joueur X"

        // Assignation des carreaux à leur textview respectif.
        carre1 = findViewById(R.id.carre1)
        carre2 = findViewById(R.id.carre2)
        carre3 = findViewById(R.id.carre3)
        carre4 = findViewById(R.id.carre4)
        carre5 = findViewById(R.id.carre5)
        carre6 = findViewById(R.id.carre6)
        carre7 = findViewById(R.id.carre7)
        carre8 = findViewById(R.id.carre8)
        carre9 = findViewById(R.id.carre9)

        // Événements lorsque le bouton est cliqué.
        start.setOnClickListener {
            val nomInscrit = nom.text.toString()

            // Identification des joueurs avant de débuter la partie.
            if (!partieEnCours) {
                if (joueurCourant == "X") {
                    nomJoueurX = nomInscrit
                    joueurX.text = "Joueur X: $nomJoueurX (Score: $pointageX)"
                    start.text = "Joueur O"
                } else if (joueurCourant == "O") {
                    nomJoueurO = nomInscrit
                    joueurO.text = "Joueur O: $nomJoueurO (Score: $pointageO)"
                    start.text = "Recommencer la partie"
                    partieEnCours = true
                }
                // Échange de tour.
                joueurCourant = if (joueurCourant == "X") "O" else "X"
            } else {
                // Recommencer la partie.
                reset()
                partieEnCours = true
            }

            // Vider la ligne de texte.
            nom.text.clear()
        }

        // Mise en place des événements au clique de chaque carré.
        carre1.setOnClickListener { selectionCarre(carre1) }
        carre2.setOnClickListener { selectionCarre(carre2) }
        carre3.setOnClickListener { selectionCarre(carre3) }
        carre4.setOnClickListener { selectionCarre(carre4) }
        carre5.setOnClickListener { selectionCarre(carre5) }
        carre6.setOnClickListener { selectionCarre(carre6) }
        carre7.setOnClickListener { selectionCarre(carre7) }
        carre8.setOnClickListener { selectionCarre(carre8) }
        carre9.setOnClickListener { selectionCarre(carre9) }
    }

    // Gestion de la sélection des carreaux par les joueurs.
    private fun selectionCarre(carre: TextView) {
        if (!partieEnCours) {
            return
        }

        // Mise à jour d'un carré avec le symbole du joueur courant.
        if (carre.text.isEmpty()) {
            carre.text = joueurCourant

            // Vérifier s'il y a victoire ou match nul.
            if (victoire()) {
                val gagnant = if (joueurCourant == "X") nomJoueurX else nomJoueurO

                // Affichage d'un message de victoire.
                val messageVictoire = "Félicitations $gagnant, vous avez gagné!"
                Toast.makeText(applicationContext, messageVictoire, Toast.LENGTH_SHORT).show()

                // Mise à jour du score.
                majPointage()

                // Lorsque la partie est terminée, on désactive vide les carrés.
                disableCarre()
            } else if (matchNul()) {
                // Affichage d'un message de match nul.
                val messageMatchNul = "Match nul."
                Toast.makeText(applicationContext, messageMatchNul, Toast.LENGTH_SHORT).show()

                // Lorsque la partie est terminée, on désactive la grille de jeu.
                disableCarre()
                // Échange de tour après un match nul.
                joueurCourant = if (joueurCourant == "X") "O" else "X"
            } else {
                joueurCourant = if (joueurCourant == "X") "O" else "X"
            }
        }
    }

    // Combinaisons gagnantes.
    private fun victoire(): Boolean {
        val lignes = arrayOf(
            arrayOf(carre1, carre2, carre3),
            arrayOf(carre4, carre5, carre6),
            arrayOf(carre7, carre8, carre9),
            arrayOf(carre1, carre4, carre7),
            arrayOf(carre2, carre5, carre8),
            arrayOf(carre3, carre6, carre9),
            arrayOf(carre1, carre5, carre9),
            arrayOf(carre3, carre5, carre7)
        )

        // Boucle récoltant le résultat de la combinaison sélectionnée.
        for (ligne in lignes) {
            if (ligne.all { it.text == joueurCourant }) {
                return true
            }
        }

        return false
    }

    // Vérification si le match est nul.
    private fun matchNul(): Boolean {
        val carres = arrayOf(
            carre1, carre2, carre3,
            carre4, carre5, carre6,
            carre7, carre8, carre9
        )

        return carres.all { it.text.isNotEmpty() }
    }

    // Désactiver la grille de jeu sans recommencer une nouvelle partie.
    private fun disableCarre() {
        carre1.setOnClickListener(null)
        carre2.setOnClickListener(null)
        carre3.setOnClickListener(null)
        carre4.setOnClickListener(null)
        carre5.setOnClickListener(null)
        carre6.setOnClickListener(null)
        carre7.setOnClickListener(null)
        carre8.setOnClickListener(null)
        carre9.setOnClickListener(null)
    }

    // Vider le tableau afin de recommencer la partie.
    private fun reset() {
        val start = findViewById<Button>(R.id.btnStart)
        start.text = "Recommencer la partie"

        // Efface le texte des carrés.
        carre1.text = ""
        carre2.text = ""
        carre3.text = ""
        carre4.text = ""
        carre5.text = ""
        carre6.text = ""
        carre7.text = ""
        carre8.text = ""
        carre9.text = ""

        // Réactiver les clics sur les carrés.
        carre1.setOnClickListener { selectionCarre(carre1) }
        carre2.setOnClickListener { selectionCarre(carre2) }
        carre3.setOnClickListener { selectionCarre(carre3) }
        carre4.setOnClickListener { selectionCarre(carre4) }
        carre5.setOnClickListener { selectionCarre(carre5) }
        carre6.setOnClickListener { selectionCarre(carre6) }
        carre7.setOnClickListener { selectionCarre(carre7) }
        carre8.setOnClickListener { selectionCarre(carre8) }
        carre9.setOnClickListener { selectionCarre(carre9) }

        // Drapeau qui permettra de jouer à nouveau sans demander de valider les joueurs.
        nomsEntres = false
    }

    // Mise à jour du pointage lorsque l'un des joueurs gagne.
    private fun majPointage() {
        if (joueurCourant == "X") {
            pointageX++
            val txtJoueurX = findViewById<TextView>(R.id.txtJoueurX)
            txtJoueurX.text = "Joueur X: $nomJoueurX (Score: $pointageX)"
        } else {
            pointageO++
            val txtJoueurO = findViewById<TextView>(R.id.txtJoueurO)
            txtJoueurO.text = "Joueur O: $nomJoueurO (Score: $pointageO)"
        }
    }
}
