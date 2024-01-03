package com.zohocrm.zoho.Service.impl;

import com.zohocrm.zoho.Entity.Lead;
import com.zohocrm.zoho.Exception.LeadExists;
import com.zohocrm.zoho.Payload.LeadDto;
import com.zohocrm.zoho.Payload.ResponsePage;
import com.zohocrm.zoho.Repository.leadRepository;
import com.zohocrm.zoho.Service.LeadService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LeadServiceimpl implements LeadService {

    private leadRepository leadRepository;
    private ModelMapper modelMapper;

    public LeadServiceimpl(com.zohocrm.zoho.Repository.leadRepository leadRepository,
                           ModelMapper modelMapper) {
        this.leadRepository = leadRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LeadDto createlead(LeadDto leadDto) {

        boolean emailExists = leadRepository.existsByEmail(leadDto.getEmail());
        boolean mobileExists= leadRepository.existsByMobile(leadDto.getMobile());

        if(emailExists){
            throw new LeadExists("Email exists -"+leadDto.getEmail());

        }
        if(mobileExists){
            throw new LeadExists("Mobile exists - "+leadDto.getMobile());

        }

         Lead lead = maptoLead(leadDto);
        /// create random id
         String lid = UUID.randomUUID().toString();
         lead.setLid(lid);

         Lead savedlead =  leadRepository.save(lead);
         LeadDto dto = maptoLeadDto(savedlead);


        return dto;
    }

    @Override
    public void deleteRecord(String lid) {
        Lead lead =  leadRepository.findById(lid).orElseThrow(
                ()-> new LeadExists("Lead with this id not present -"+ lid )
        );
        leadRepository.deleteById(lid);

    }

    @Override
    public ResponsePage getallLeads(int pageNo, int pageSize, String sortDir, String sortBy) {

        Sort sort =sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Lead> all = leadRepository.findAll(pageable);
        List<Lead> lead = all.getContent();

        List<LeadDto> leadDtos = lead.stream().map(s->maptoLeadDto(s)).collect(Collectors.toList());

        ResponsePage responsePage = new ResponsePage();
        responsePage.setContent(leadDtos);
        responsePage.setPageNo(all.getNumber());
        responsePage.setPageSize(all.getSize());
        responsePage.setTotalpage(all.getTotalPages());
        responsePage.setTotalElement(all.getTotalElements());
        responsePage.setLastpage(all.isLast());


        return responsePage ;
    }

    @Override
    public List<Lead> getLeadsExcelReports() {
       return leadRepository.findAll();
    }

    @Override
    public List <LeadDto> findAll() {
        List<Lead> leads = leadRepository.findAll();
        List<LeadDto> dtos = leads.stream().map(s->maptoLeadDto(s)).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public List<LeadDto> CsvfindAll() {
        List<Lead> leads  = leadRepository.findAll();
        List<LeadDto> dtos = leads.stream().map(s->maptoLeadDto(s)).collect(Collectors.toList());

        return dtos;
    }

    Lead maptoLead(LeadDto leadDto){
    return modelMapper.map(leadDto , Lead.class);
    }

    LeadDto maptoLeadDto (Lead lead){
        return modelMapper.map(lead , LeadDto.class);
    }
}
