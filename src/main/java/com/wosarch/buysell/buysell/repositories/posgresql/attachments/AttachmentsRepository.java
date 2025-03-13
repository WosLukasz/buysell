package com.wosarch.buysell.buysell.repositories.posgresql.attachments;

import com.wosarch.buysell.buysell.model.attachments.Attachment;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentsRepository extends CrudRepository<Attachment, Long>, TemplateAttachmentsRepository {
}
