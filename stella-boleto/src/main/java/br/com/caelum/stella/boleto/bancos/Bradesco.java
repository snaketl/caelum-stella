package br.com.caelum.stella.boleto.bancos;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Emissor;
import static br.com.caelum.stella.boleto.utils.StellaStringUtils.leftPadWithZeros;

/**
 * Gera dados de um boleto relativos ao Banco Bradesco.
 * 
 * @see <a href="http://www.bradesco.com.br/br/pj/conteudo/sol_rec
 * /pdf/manualtecnico.pdf" >MANUAL DO BLOQUETO DE COBRANÇA </a>
 * 
 * @author Leonardo Bessa
 * 
 */
public class Bradesco extends AbstractBanco implements Banco {

	private static final long serialVersionUID = 1L;


	private static final String NUMERO_BRADESCO = "237";

	private static final String DIGITO_NUMERO_BRADESCO = "2";

	@Override
	public String geraCodigoDeBarrasPara(Boleto boleto) {
		Emissor emissor = boleto.getEmissor();
		StringBuilder campoLivre = new StringBuilder();
		campoLivre.append(emissor.getAgenciaFormatado());
		campoLivre.append(getCarteiraDoEmissorFormatado(emissor));
		campoLivre.append(getNossoNumeroDoEmissorFormatado(emissor));
		campoLivre.append(getContaCorrenteDoEmissorFormatado(emissor));
		campoLivre.append("0");
		return new CodigoDeBarrasBuilder(boleto).comCampoLivre(campoLivre);
	}

	@Override
	public String getNumeroFormatadoComDigito() {
		StringBuilder builder = new StringBuilder();
		builder.append(getNumeroFormatado()).append("-");
		return builder.append(DIGITO_NUMERO_BRADESCO).toString();
	}

	@Override
	public String getNumeroFormatado() {
		return NUMERO_BRADESCO;
	}

	@Override
	public java.net.URL getImage() {
		String arquivo = "/br/com/caelum/stella/boleto/img/%s.png";
		String imagem = String.format(arquivo, getNumeroFormatado());
		return getClass().getResource(imagem);
	}

	public String getNumeroConvenioDoEmissorFormatado(Emissor emissor) {
		return leftPadWithZeros(emissor.getNumeroConvenio(), 7);
	}

	@Override
	public String getContaCorrenteDoEmissorFormatado(Emissor emissor) {
		return leftPadWithZeros(emissor.getContaCorrente(), 7);
	}

	@Override
	public String getCarteiraDoEmissorFormatado(Emissor emissor) {	
		return leftPadWithZeros(emissor.getCarteira(), 2);
	}

	@Override
	public String getNossoNumeroDoEmissorFormatado(Emissor emissor) {
		return leftPadWithZeros(emissor.getNossoNumero(), 11);
	}

	public String getDigitoNossoNumeroDoEmissorFormatado(Emissor emissor) {
		return emissor.getDigitoNossoNumero();
	}

	@Override
	public String getNossoNumeroECodDocumento(Boleto boleto) {
		Emissor emissor = boleto.getEmissor();
		StringBuilder builder = new StringBuilder().append(leftPadWithZeros(emissor.getCarteira(),2));
		builder.append("/").append(getNossoNumeroDoEmissorFormatado(emissor));
		return builder.append(getDigitoNossoNumero(emissor)).toString();
	}

	private String getDigitoNossoNumero(Emissor emissor) {
		return emissor.getDigitoNossoNumero() != null 
			&& !emissor.getDigitoNossoNumero().isEmpty() 
				? "-" + emissor.getDigitoNossoNumero() : "";
	}

}