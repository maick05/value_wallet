package br.speck.valuewallet.api.application.service;

public interface CommandService<Response, ParamsDTO> {
    Response execute(ParamsDTO paramsDTO);
}
