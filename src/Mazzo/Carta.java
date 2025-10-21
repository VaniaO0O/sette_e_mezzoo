package Mazzo;

import java.io.Serial;
import java.io.Serializable;

/**
 * Una carta ha un seme e un valore
 */
public class Carta implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Seme seme;
    private final Valore valore;

    public Carta(Seme seme, Valore valore) {
        this.seme = seme;
        this.valore = valore;
    }

    public boolean isMatta(){
        return this.seme.equals(Seme.DENARI) && this.valore.equals(Valore.RE);
    }

    // get seme e valore
    public float getRealVal(){
        return valore.getRealVal();
    }

    @Override
    public String toString()
    {
        return seme.toString() +  valore.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Carta c))
            return false;

        return seme == c.seme && valore == c.valore;
    }

}