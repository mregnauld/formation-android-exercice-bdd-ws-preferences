package com.formationandroid.bddwspreferences;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MemosAdapter extends RecyclerView.Adapter<MemoViewHolder>
{
	
	// Liste d'objets métier :
	private List<MemoDTO> listeMemoDTO = null;
	
	
	/**
	 * Constructeur.
	 * @param listeMemoDTO Liste de mémos
	 */
	public MemosAdapter(List<MemoDTO> listeMemoDTO)
	{
		this.listeMemoDTO = listeMemoDTO;
	}
	
	@Override
	public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View viewMemo = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false);
		return new MemoViewHolder(viewMemo);
	}
	
	@Override
	public void onBindViewHolder(MemoViewHolder holder, int position)
	{
		holder.getTextViewIntitule().setText(listeMemoDTO.get(position).getIntitule());
	}
	
	@Override
	public int getItemCount()
	{
		return listeMemoDTO.size();
	}
	
	/**
	 * Ajout d'un mémo à la liste.
	 * @param memoDTO Mémo
	 */
	public void ajouterMemo(MemoDTO memoDTO)
	{
		listeMemoDTO.add(0, memoDTO);
		notifyItemInserted(0);
	}
	
}
