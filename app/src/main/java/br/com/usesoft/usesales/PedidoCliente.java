package br.com.usesoft.usesales;

import java.math.BigDecimal;

/**
 * Created by Eduardo Lucas on 23/12/2015.
 * Updated by Eduardo Lucas on 01/04/2016.
 */
public class PedidoCliente {
    private Long       _id;
    private Long       numeroPedido;
    private String     tipoPedido;           // Pode ser Pedido ou Pré-Pedido
    private String     seriePedido;
    private String     subSeriePedido;
    private String     dataPedido;
    private String     horaPedido;
    private int        codigoCliente;
    private int        codigoVendedor;
    private int        codigoFormaPagamento;
    private int        codigoPrazoPagamento;
    private Long       codigoEmpresa;
    private BigDecimal valorTotal;
    private String     origem;
    private String     status;
    private String     observacao;

    public PedidoCliente(Long       _id,
                         Long       numeroPedido,
                         String     tipoPedido,
                         String     seriePedido,
                         String     subSeriePedido,
                         String     dataPedido,
                         String     horaPedido,
                         int        codigoCliente,
                         int        codigoVendedor,
                         int        codigoFormaPagamento,
                         int        codigoPrazoPagamento,
                         Long       codigoEmpresa,
                         BigDecimal valorTotal,
                         String     origem,
                         String     status,
                         String     observacao) {
        this._id                  = _id;
        this.numeroPedido         = numeroPedido;
        this.tipoPedido           = tipoPedido;
        this.codigoEmpresa        = codigoEmpresa;
        this.codigoCliente        = codigoCliente;
        this.codigoFormaPagamento = codigoFormaPagamento;
        this.codigoPrazoPagamento = codigoPrazoPagamento;
        this.codigoVendedor       = codigoVendedor;
        this.dataPedido           = dataPedido;
        this.horaPedido           = horaPedido;
        this.origem               = origem;
        this.seriePedido          = seriePedido;
        this.status               = status;
        this.subSeriePedido       = subSeriePedido;
        this.valorTotal           = valorTotal;
        this.observacao           = observacao;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public Long getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Long numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public Long getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Long codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoFormaPagamento() {
        return codigoFormaPagamento;
    }

    public void setCodigoFormaPagamento(int codigoFormaPagamento) {
        this.codigoFormaPagamento = codigoFormaPagamento;
    }

    public int getCodigoPrazoPagamento() {
        return codigoPrazoPagamento;
    }

    public void setCodigoPrazoPagamento(int codigoPrazoPagamento) {
        this.codigoPrazoPagamento = codigoPrazoPagamento;
    }

    public int getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(int codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getSeriePedido() {
        return seriePedido;
    }

    public void setSeriePedido(String seriePedido) {
        this.seriePedido = seriePedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubSeriePedido() {
        return subSeriePedido;
    }

    public void setSubSeriePedido(String subSeriePedido) {
        this.subSeriePedido = subSeriePedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
