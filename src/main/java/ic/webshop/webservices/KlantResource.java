package ic.webshop.webservices;

import java.util.ArrayList;
import javax.ws.rs.core.Response;
import ic.webshop.domain.Klant;
import ic.webshop.persistence.KlantDAO;

public class KlantResource implements KlantService{
	KlantDAO klantDAO = new KlantDAO();
	
	@Override
	public ArrayList<Klant> getKlanten() {
		return null;
	}

	@Override
	public Response getKlant(int klantID) {
		return null;
	}

	@Override
	public Response deleteKlant(int klantID) {
		return null;
	}

	@Override
	public Response updateKlant(int klantID, Klant klant) {
		return null;
	}

	@Override
	public Response createKlant(Klant klant) {
		return null;
	}

}
