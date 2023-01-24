package org.defendev.common.domain.resrep;

import java.util.List;

public interface ICollectionResRep<DTO extends IBaseDto> {

    List<DTO> getItems();

    ICollectionMeta getMeta();

}
