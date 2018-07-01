package com.formationandroid.bddwspreferences;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MemosAdapter extends RecyclerView.Adapter<MemosAdapter.MemoViewHolder>
{
	
	// Activité :
	private MainActivity mainActivity = null;
	
	// Liste d'objets métier :
	private List<MemoDTO> listeMemoDTO = null;
	
	
	/**
	 * Constructeur.
	 * @param mainActivity MainActivity
	 * @param listeMemoDTO Liste de mémos
	 */
	public MemosAdapter(MainActivity mainActivity, List<MemoDTO> listeMemoDTO)
	{
		this.mainActivity = mainActivity;
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
		holder.textViewIntitule.setText(listeMemoDTO.get(position).intitule);
	}
	
	@Override
	public int getItemCount()
	{
		return listeMemoDTO.size();
	}
	
	/**
	 * Ajout d'un mémo à la liste.
	 * @param listeMemoDTO Liste de MemoDTO
	 */
	public void actualiserMemos(List<MemoDTO> listeMemoDTO)
	{
		this.listeMemoDTO = listeMemoDTO;
		notifyDataSetChanged();
	}
	
	/**
	 * Retourne le MemoDTO à la position indiquée.
	 * @param position Position dans la liste
	 * @return MemoDTO
	 */
	public MemoDTO getMemoDTOParPosition(int position)
	{
		return listeMemoDTO.get(position);
	}
	
	
	/**
	 * ViewHolder.
	 */
	class MemoViewHolder extends RecyclerView.ViewHolder
	{
		
		// Vue intitulé mémo :
		TextView textViewIntitule = null;
		
		
		/**
		 * Constructeur.
		 * @param itemView Vue item
		 */
		MemoViewHolder(final View itemView)
		{
			super(itemView);
			textViewIntitule = itemView.findViewById(R.id.memo_intitule);
			
			// listener :
			itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					mainActivity.onClicItem(getAdapterPosition());
				}
			});
		}
		
	}
	
}
