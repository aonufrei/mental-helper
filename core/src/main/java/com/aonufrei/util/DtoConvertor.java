package com.aonufrei.util;

public interface DtoConvertor<M, IDTO, ODTO> {

	M toModel(IDTO dto);

	ODTO toOut(M model);

}
