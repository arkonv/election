package ru.smartsarov.election;

public final class Constants {

	public static final String CIKRF_FIND_UIK_URL = "http://www.cikrf.ru/services/lk_tree/?id=";
	public static final String CIKRF_UIK_INFO_URL = "http://www.cikrf.ru/services/lk_address/?do=result_uik";
	public static final String CIKRF_UIK_ADDRESS_URL = "http://www.cikrf.ru/services/lk_address/";
	public static final String JSON_INDENT = "    ";
	public static final String UIK_BAD_NUMBER_MESSAGE = "Информируем Вас, что сведения об избирательном участке по введенным Вами данным в системе ГАС \"ВЫБОРЫ\" на момент создания запроса отсутствуют.";
	public static final String UIK_BAD_NUMBER_MESSAGE2 = "Информируем Вас, что данные об избирательном участке по введенному Вами адресу места жительства в системе ГАС \"ВЫБОРЫ\" на момент создания запроса отсутствуют.";
	
	/**  Саров  **/
	public static final String SAROV_DB_NAME = "sarov.db";
	public static final int NIZHNY_NOVGOROD_REGION = 52;
	public static final String UIK_VYBORY_IZBIRKOM_RU_NNOV = "http://www.nnov.vybory.izbirkom.ru/region/nnov/?action=ik&vrn=";
	public static final String SAROV_ADDRESS = "Россия, Нижегородская область, Саров, ";
	public static final String SAROV_STREETS = "http://www.cikrf.ru/services/lk_tree/?id=9041113462";
	public static final String SAROV_UIKS = "http://www.nnov.vybory.izbirkom.ru/region/nnov?action=ikTree&region=52&vrn=4524024146067&onlyChildren=true&id=4524024146067";
	public static final String SAROV_TIK = "http://www.nnov.vybory.izbirkom.ru/region/nnov/?action=ik&vrn=4524024146067";

	/**  Дубна  **/
	public static final String DUBNA_DB_NAME = "dubna.db";
	public static final int MOSCOW_REGION = 50;
	public static final String UIK_VYBORY_IZBIRKOM_RU_MOSCOW_REGION = "http://www.moscow_reg.vybory.izbirkom.ru/region/moscow_reg/?action=ik&vrn=";
	public static final String DUBNA_ADDRESS = "Россия, Московская область, Дубна, ";
	public static final String DUBNA_STREETS = "http://www.cikrf.ru/services/lk_tree/?id=9006648809";
	public static final String DUBNA_UIKS = "http://www.moscow_reg.vybory.izbirkom.ru/region/moscow_reg/?action=ikTree&region=50&vrn=4504008145490&onlyChildren=true&id=4504008145490";
	public static final String DUBNA_TIK = "http://www.moscow_reg.vybory.izbirkom.ru/region/moscow_reg/?action=ik&vrn=4504008145490";

	private Constants() {
	}

}